package com.robots.models;

import com.robots.models.trades.TradeType;

import java.time.ZonedDateTime;

public class Trade {

    private TradeType tradeType;

    private double priceEntry;

    private double priceExit;

    private double pnl;

    private ZonedDateTime dateTimeEntry;

    private ZonedDateTime dateTimeExit;

    private double quantity;

    private int nbBars;

    public Trade(TradeType tradeType, ZonedDateTime dateTimeEntry, double priceEntry, double quantity) {
        this.tradeType = tradeType;
        this.dateTimeEntry = dateTimeEntry;
        this.priceEntry = priceEntry;
        this.quantity = quantity;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public double getPriceEntry() {
        return priceEntry;
    }

    public double getPriceExit() {
        return priceExit;
    }

    public void setPriceExit(double priceExit) {
        this.priceExit = priceExit;
        this.pnl = this.priceExit - priceEntry;
    }

    public double getPnl() {
        return pnl;
    }

    public ZonedDateTime getDateTimeEntry() {
        return dateTimeEntry;
    }

    public ZonedDateTime getDateTimeExit() {
        return dateTimeExit;
    }

    public void setDateTimeExit(ZonedDateTime dateTimeExit) {
        this.dateTimeExit = dateTimeExit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getNbBars() {
        return nbBars;
    }

    public void setNbBars(int nbBars) {
        this.nbBars = nbBars;
    }

}
