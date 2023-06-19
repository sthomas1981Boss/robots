package com.robots.models.orders.standard;

import com.robots.models.orders.Order;
import com.robots.models.orders.OrderDirection;
import com.robots.models.orders.OrderLot;

public class OrderStandard extends Order {

    public OrderStandard(OrderDirection direction, int nbContracts, String name) {
        super(direction, nbContracts, name);
        this.units = 100000;
        this.costPerPips = 10;
        this.nominal = this.units * this.costPerPips * this.nbContracts;
        this.margin = 1 / this.leverage * this.nominal;
        this.lot = OrderLot.STANDARD;
    }

}
