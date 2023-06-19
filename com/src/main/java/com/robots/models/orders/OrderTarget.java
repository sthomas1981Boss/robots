package com.robots.models.orders;


public class OrderTarget extends Order {

    protected double target;

    public OrderTarget(OrderDirection direction, int nbContracts, double target, String name) {
        super(direction, nbContracts, name);
        this.target = target;
    }

}
