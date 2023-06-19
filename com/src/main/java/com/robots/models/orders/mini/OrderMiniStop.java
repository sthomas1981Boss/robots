package com.robots.models.orders.mini;

import com.robots.models.orders.OrderDirection;
import com.robots.models.orders.OrderType;

public class OrderMiniStop extends OrderMiniTarget {
    public OrderMiniStop(OrderDirection direction, int nbContracts, double target, String name) {
        super(direction, nbContracts, target, name);
        this.type = OrderType.ORDER_STOP;
    }
}
