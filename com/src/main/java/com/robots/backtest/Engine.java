package com.robots.backtest;


import com.robots.models.orders.Order;
import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.timeseries.Point;
import com.robots.models.trades.Trade;
import com.robots.models.trades.TradeType;
import com.robots.strategies.Results;
import com.robots.strategies.Strategy;

import java.time.ZonedDateTime;
import java.util.*;

public class Engine implements Runnable {

    protected double initialCapital;
    protected List<Order> ordersToProcess;
    private Map<String, Trade> onGoingTrades;
    private List<Trade> closedTrades;
    protected OhlcTimeserie ohlcAsk;
    protected OhlcTimeserie ohlcBid;
    protected Strategy strategy;
    protected Results results;

    public Engine(double initialCapital, Strategy strategy, OhlcTimeserie ohlcAsk, OhlcTimeserie ohlcBid) {
        this.initialCapital = initialCapital;
        this.strategy = strategy;
        this.ohlcAsk = ohlcAsk;
        this.ohlcBid = ohlcBid;
        this.results = new Results();
        this.ordersToProcess = new ArrayList<Order>();
        this.onGoingTrades = new HashMap<String, Trade>();
        this.closedTrades = new ArrayList<Trade>();
    }

    @Override
    public void run() {

        // Set OHLC to strategy
        this.strategy.setOhlcAsk(this.ohlcAsk);
        this.strategy.setOhlcBid(this.ohlcBid);

        // Init strategy
        this.strategy.init();

        // Init equity
        double equity = this.initialCapital;

        // Main loop
        for (int i = 0 ; i < this.ohlcAsk.size() ; i++) {
            // Processing orders => trades - in this position we process the order with the dt+1 when orders was issued
            processOrders(i);

            // Apply strategy
            this.strategy.run(i);

            // Compute equity
            ZonedDateTime dt = this.ohlcAsk.get(i).getDt();
            equity += computeEquity(i);
            this.results.addEquity(new Point(dt, equity));
        }

        // Finalize strategy
        this.strategy.finalize();
    }

    private void processOrders(int index) {
        // Retrieve all new orders if any
        if (!this.strategy.getOrders().isEmpty()) {
            this.ordersToProcess.addAll(this.strategy.getOrders());
            this.strategy.clearOrders();
        }

        // Process all orders
        for (Order order : this.ordersToProcess) {
            switch(order.getType()) {
                case ORDER_MARKET: processMarketOrder(index, order);

            }
        }

    }

    private void processMarketOrder(int index, Order order) {
        // Case 1 : no action on current on-going trades => new trade
        if (order.getTradeId() == null || !onGoingTrades.containsKey(order.getTradeId())) {
            // Check margin

            // Create the trade
            double priceEntry = this.ohlcBid.get(index).getOpen();
            ZonedDateTime dt = this.ohlcBid.get(index).getDt();
            Trade trade = new Trade(order, priceEntry, dt);
            this.onGoingTrades.put(trade.getId(), trade);
            this.strategy.pushOnGoingTrade(trade.getId(), trade);
            // Create the stop loss order if required
            if (trade.hasStopLossOrder()) {
                Order sl = trade.getStopLossOrder();
                this.ordersToProcess.add(sl);
            }
            // Create the take profit order if required
            if (trade.hasTakeProfitOrder()) {
                Order tp = trade.getTakeProfitOrder();
                this.ordersToProcess.add(tp);
            }
        } else { // Case 2 : close the trade
            // Get the on-going trade and update information
            Trade onGoingTrade = this.onGoingTrades.get(order.getTradeId());
            onGoingTrade.setDateTimeExit(this.ohlcAsk.getIndex().get(index));
            onGoingTrade.setPriceExit(this.ohlcAsk.get(index).getOpen());
            // Close the trade
            closedTrades.add(onGoingTrade);
            this.onGoingTrades.remove(order.getTradeId());
            this.strategy.closeTrade(order.getTradeId());
            this.results.addTrade(onGoingTrade);
        }
    }

    private double computeEquity(int index) {
        double pnl = 0;
        for (Trade trade : this.onGoingTrades.values()) {
            if (trade.getTradeType() == TradeType.LONG) {
                double value = (this.ohlcAsk.get(index).getClose() - this.ohlcAsk.get(index).getOpen())*10000;
                value = value * trade.getInitialOrder().getCostPerPips() * trade.getInitialOrder().getNbContracts();
                pnl += value;
            }
        }
        return pnl;
    }

    public Results getResults() {
        return results;
    }
}
