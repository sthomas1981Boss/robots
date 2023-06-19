package com.robots.models.orders.standard;

import com.robots.models.orders.OrderDirection;
import com.robots.models.orders.OrderType;

public class OrderStandardStopLoss extends OrderStandardTarget {
    public OrderStandardStopLoss(OrderDirection direction, int nbContracts, double target, String name) {
        super(direction, nbContracts, target, name);
        this.type = OrderType.ORDER_STOP_LOSS;
    }
}
