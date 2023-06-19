package com.robots.models.orders.mini;

import com.robots.models.orders.OrderDirection;
import com.robots.models.orders.OrderLot;
import com.robots.models.orders.OrderType;

public class OrderMiniMarket extends OrderMini {
    public OrderMiniMarket(OrderDirection direction, int nbContracts, String name) {
        super(direction, nbContracts, name);
        this.type = OrderType.ORDER_MARKET;
    }
}
