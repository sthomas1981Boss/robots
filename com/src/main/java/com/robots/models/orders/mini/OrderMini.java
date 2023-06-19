package com.robots.models.orders.mini;

import com.robots.models.orders.Order;
import com.robots.models.orders.OrderDirection;
import com.robots.models.orders.OrderLot;

public class OrderMini extends Order {

    public OrderMini(OrderDirection direction, int nbContracts, String name) {
        super(direction, nbContracts, name);
        this.units = 10000;
        this.costPerPips = 1;
        this.nominal = this.units * this.costPerPips * this.nbContracts;
        this.margin = 1 / this.leverage * this.nominal;
        this.lot = OrderLot.MINI;
    }

}
