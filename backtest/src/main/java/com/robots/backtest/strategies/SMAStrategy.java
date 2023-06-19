package com.robots.backtest.strategies;

import com.robots.chart.TradingChart;
import com.robots.models.orders.*;
import com.robots.models.timeseries.Point;
import com.robots.models.timeseries.Timeserie;
import com.robots.strategies.Indicator;
import com.robots.strategies.Rule;
import com.robots.strategies.Strategy;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class SMAStrategy extends Strategy {

    Timeserie signalLong;
    private Map<ZonedDateTime, Point> signalLongMap;

    @Override
    public void init() {

        Timeserie close = this.ohlcAsk.getClose();
        Timeserie sma10 = Indicator.SMA(close, 4);
        Timeserie sma20 = Indicator.SMA(close, 16);

        // long signal
        signalLong = Rule.CrossOver(sma10,sma20);
        signalLongMap = signalLong.getMap();
    }
    @Override
    public void run(int index) {
        ZonedDateTime dt = getDateTime(index);
        if(signalLongMap.containsKey(dt)){
            if(signalLongMap.get(dt).getValue() == 1){
                Order order = OrderFactory.create(OrderLot.MINI, OrderType.ORDER_MARKET,
                        OrderDirection.LONG, 5, null);
                this.orders.add(order);
            }
        }
    }

    @Override
    public void finalize() {

    }


}
