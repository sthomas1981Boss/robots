package com.robots.models.orders.mini;

import com.robots.models.orders.OrderDirection;
import com.robots.models.orders.OrderLot;
import com.robots.models.orders.OrderTarget;

public class OrderMiniTarget extends OrderTarget {
    public OrderMiniTarget(OrderDirection direction, int nbContracts, double target, String name) {
        super(direction, nbContracts, target, name);
        this.units = 10000;
        this.costPerPips = 1;
        this.nominal = this.units * this.costPerPips * this.nbContracts;
        this.margin = 1 / this.leverage * this.nominal;
        this.lot = OrderLot.MINI;
    }
}
