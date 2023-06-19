package com.robots.models.trades;

import com.robots.models.orders.*;

import java.time.ZonedDateTime;
import java.util.Random;

public class Trade {

    protected String id;
    protected Order initialOrder;
    protected Order stopLossOrder;
    protected Order takeProfitOrder;
    protected TradeType tradeType;
    protected double priceEntry;
    protected ZonedDateTime dateTimeEntry;
    protected double priceExit;
    protected ZonedDateTime dateTimeExit;

    public Trade(Order initialOrder, double priceEntry, ZonedDateTime dateTimeEntry) {
        this.initialOrder = initialOrder;
        this.priceEntry = priceEntry;
        this.dateTimeEntry = dateTimeEntry;
        switch (this.initialOrder.getDirection()){
            case LONG:this.tradeType = TradeType.LONG;break;
            case SHORT:this.tradeType = TradeType.SHORT;break;
        }
        this.id = generateRandomKey(8);
    }

    public boolean hasStopLossOrder() {
        if(initialOrder.getStopLossDistance() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean hasTakeProfitOrder() {
        if(initialOrder.getTakeProfitDistance() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public Order getStopLossOrder(){
        if (hasStopLossOrder()) {
            switch(initialOrder.getDirection()) {
                case LONG:
                    stopLossOrder = OrderFactory.create(initialOrder.getLot(),
                            OrderType.ORDER_STOP_LOSS,
                            OrderDirection.SHORT,
                            initialOrder.getNbContracts(),
                            (this.priceEntry * 10000 - initialOrder.getStopLossDistance())/10000,
                            initialOrder.getTradeId());
                    break;

                case SHORT:
                    stopLossOrder = OrderFactory.create(initialOrder.getLot(),
                            OrderType.ORDER_STOP_LOSS,
                            OrderDirection.LONG,
                            initialOrder.getNbContracts(),
                            (this.priceEntry * 10000 + initialOrder.getStopLossDistance())/10000,
                            initialOrder.getTradeId());
                    break;
            }
        }
        return stopLossOrder;
    }

    public Order getTakeProfitOrder(){
        if (hasTakeProfitOrder()) {
            switch(initialOrder.getDirection()) {
                case LONG:
                    takeProfitOrder = OrderFactory.create(initialOrder.getLot(),
                            OrderType.ORDER_LIMIT,
                            OrderDirection.SHORT,
                            initialOrder.getNbContracts(),
                            (this.priceEntry * 10000 + initialOrder.getTakeProfitDistance())/10000,
                            initialOrder.getTradeId());
                    break;

                case SHORT:
                    takeProfitOrder = OrderFactory.create(initialOrder.getLot(),
                            OrderType.ORDER_LIMIT,
                            OrderDirection.LONG,
                            initialOrder.getNbContracts(),
                            (this.priceEntry * 10000 - initialOrder.getTakeProfitDistance())/10000,
                            initialOrder.getTradeId());
                    break;
            }
        }
        return takeProfitOrder;
    }

    public String getId(){
        return this.initialOrder.getTradeId();
    }

    public double getPriceEntry() {
        return priceEntry;
    }

    public double getPriceExit() {
        return priceExit;
    }

    public void setPriceExit(double priceExit) {
        this.priceExit = priceExit;
    }

    public ZonedDateTime getDateTimeExit() {
        return dateTimeExit;
    }

    public void setDateTimeExit(ZonedDateTime dateTimeExit) {
        this.dateTimeExit = dateTimeExit;
    }

    public ZonedDateTime getDateTimeEntry() {
        return dateTimeEntry;
    }

    public Order getInitialOrder() {
        return initialOrder;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    private static String generateRandomKey(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            char randomChar = ALPHA_NUMERIC_STRING.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

}
