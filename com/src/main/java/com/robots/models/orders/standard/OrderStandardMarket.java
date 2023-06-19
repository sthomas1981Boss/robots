package com.robots.models.orders.standard;

import com.robots.models.orders.OrderDirection;
import com.robots.models.orders.OrderType;

public class OrderStandardMarket extends OrderStandard {
    public OrderStandardMarket(OrderDirection direction, int nbContracts, String name) {
        super(direction, nbContracts, name);
        this.type = OrderType.ORDER_MARKET;
    }
}
