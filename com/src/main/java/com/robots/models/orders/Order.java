package com.robots.models.orders;

public abstract class Order {

    protected String tradeId;
    protected OrderLot lot;
    protected OrderType type;
    protected OrderDirection direction;
    protected int nbContracts; // mini 1 contracts
    protected double costPerPips; // 1$ per pips
    protected double nominal; // unit * cours * nb contracts
    protected int units; // 10,000 per mini contracts et 100,000 per contracts
    protected double margin; // 1/leverage x unit x nbcontracts
    protected double leverage = 30; // 30:1
    protected double openCost = 1.13; // spread, avg ig = 1.13 pip
    protected int stopLossDistance = 0; // in pips
    protected int takeProfitDistance = 0; // in pips

    protected Order(OrderDirection direction, int nbContracts, String tradeId) {
        this.direction = direction;
        this.nbContracts = nbContracts;
        this.tradeId = tradeId;
    }

    public void setStopLossDistance(int stopLossDistance) {
        this.stopLossDistance = stopLossDistance;
    }

    public void setTakeProfitDistance(int takeProfitDistance) {
        this.takeProfitDistance = takeProfitDistance;
    }

    public String getTradeId() {
        return tradeId;
    }

    public int getNbContracts() {
        return nbContracts;
    }

    public double getMargin() {
        return margin;
    }

    public double getOpenCost() {
        return openCost;
    }

    public OrderType getType() {
        return type;
    }

    public OrderDirection getDirection() {
        return direction;
    }

    public int getStopLossDistance() {
        return stopLossDistance;
    }

    public int getTakeProfitDistance() {
        return takeProfitDistance;
    }

    public OrderLot getLot() {
        return lot;
    }

    public double getCostPerPips() {
        return costPerPips;
    }
}
