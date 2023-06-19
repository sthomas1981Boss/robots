package com.robots.models.orders;

import com.robots.models.orders.mini.OrderMiniLimit;
import com.robots.models.orders.mini.OrderMiniMarket;
import com.robots.models.orders.mini.OrderMiniStop;
import com.robots.models.orders.mini.OrderMiniStopLoss;
import com.robots.models.orders.standard.OrderStandardLimit;
import com.robots.models.orders.standard.OrderStandardMarket;
import com.robots.models.orders.standard.OrderStandardStop;
import com.robots.models.orders.standard.OrderStandardStopLoss;

public class OrderFactory {

    public static Order create(OrderLot lot, OrderType type, OrderDirection direction, int nbContracts, String name) {
        Order order = null;
        switch (lot) {
            case MINI:
                switch (type) {
                    case ORDER_MARKET:
                        order = new OrderMiniMarket(direction, nbContracts, name);
                        break;
                }
                ;
                break;
            case STANDARD:
                switch (type) {
                    case ORDER_MARKET:
                        order = new OrderStandardMarket(direction, nbContracts, name);
                        break;
                }
                ;
                break;
        }

        return order;
    }

    public static OrderTarget create(OrderLot lot, OrderType type, OrderDirection direction, int nbContracts, double target, String name) {
        OrderTarget order = null;
        switch (lot) {
            case MINI:
                switch (type) {
                    case ORDER_STOP:
                        order = new OrderMiniStop(direction, nbContracts, target, name);
                    case ORDER_STOP_LOSS:
                        order = new OrderMiniStopLoss(direction, nbContracts, target, name);
                    case ORDER_LIMIT:
                        order = new OrderMiniLimit(direction, nbContracts, target, name);
                }
                ;
            case STANDARD:
                switch (type) {
                    case ORDER_STOP:
                        order = new OrderStandardStop(direction, nbContracts, target, name);
                    case ORDER_STOP_LOSS:
                        order = new OrderStandardStopLoss(direction, nbContracts, target, name);
                    case ORDER_LIMIT:
                        order = new OrderStandardLimit(direction, nbContracts, target, name);
                }
                ;
        }
        return order;
    }


}
