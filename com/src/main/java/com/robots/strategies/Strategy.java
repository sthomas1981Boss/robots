package com.robots.strategies;

import com.robots.models.orders.Order;
import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.trades.Trade;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Strategy {

    protected OhlcTimeserie ohlcAsk;
    protected OhlcTimeserie ohlcBid;
    protected List<Order> orders;
    protected Map<String, Trade> onGoingTrades;

    protected Strategy(){
        onGoingTrades = new HashMap<String, Trade>();
        orders = new ArrayList<Order>();
    }

    public abstract void init();

    public abstract void run(int index);

    public abstract void finalize();

    public void setOhlcAsk(OhlcTimeserie ohlcAsk) {
        this.ohlcAsk = ohlcAsk;
    }

    public void setOhlcBid(OhlcTimeserie ohlcBid) {
        this.ohlcBid = ohlcBid;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void clearOrders(){
        orders.clear();
    }

    public void pushOnGoingTrade(String id, Trade trade) {
        onGoingTrades.put(id, trade);
    }

    public void closeTrade(String id) {
        onGoingTrades.remove(id);
    }

    protected ZonedDateTime getDateTime(int index) {
        return ohlcAsk.getIndex().get(index);
    }

}
