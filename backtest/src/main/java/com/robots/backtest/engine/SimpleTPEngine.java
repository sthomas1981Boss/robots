package com.robots.backtest.engine;

import com.robots.strategies.Results;
import com.robots.models.Trade;
import com.robots.models.trades.TradeType;
import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.timeseries.Point;
import com.robots.models.timeseries.Timeserie;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class SimpleTPEngine extends Engine {

    public SimpleTPEngine() {
    }

    public Results launch(OhlcTimeserie ohlc, Timeserie signalLong, Timeserie takeProfit) {

        // Ensure we get the correct data
        Map<ZonedDateTime, Point> closeMap = ohlc.getClose().getMap();
        Map<ZonedDateTime, Point> openMap = ohlc.getOpen().getMap();
        Map<ZonedDateTime, Point> signalLongMap = signalLong.getMap();
        Map<ZonedDateTime, Point> tpMap = takeProfit.getMap();
        List<ZonedDateTime> indexes = ohlc.getClose().getIndex();

        // Holder for results of the strategy
        Results results = new Results();
        results.addEquity(new Point(indexes.get(0), capital));

        // Trade temporary data
        ZonedDateTime dateEntry = indexes.get(0);
        double entryPrice = 0;
        int nbBars = 0;
        boolean isOngoingTrade = false;
        TradeType direction = TradeType.LONG;

        for (int i = 1; i<indexes.size()-1; i++) {

            ZonedDateTime previousIndex = indexes.get(i-1);
            ZonedDateTime index = indexes.get(i);
            ZonedDateTime nextIndex = indexes.get(i+1);

            if (isOngoingTrade) {
                if (direction == TradeType.LONG) {
                    nbBars += 1;

                    // update equity
                    double previousValue = results.getEquity().get(i-1).getValue();
                    double pnl = closeMap.get(index).getValue() - openMap.get(index).getValue();
                    results.addEquity(new Point(index, previousValue + pnl*10000*100));

                    // close long trade if needed
                    if (tpMap.get(index).getValue() == 1) {
                        double exitPrice = openMap.get(nextIndex).getValue();
                        Trade longTrade = new Trade(TradeType.LONG, dateEntry, entryPrice, 0);
                        longTrade.setPriceExit(exitPrice);
                        longTrade.setDateTimeExit(nextIndex);
                        longTrade.setNbBars(nbBars);
                        //results.addTrade(longTrade);

                        isOngoingTrade = false;
                        entryPrice = 0;
                        nbBars = 0;
                    }
                }
            } else {
                // update equity
                double previousValue = results.getEquity().get(i-1).getValue();
                results.addEquity(new Point(index, previousValue));
            }

            // Open long trade
            if (signalLongMap.containsKey(previousIndex) && signalLongMap.containsKey(index) &&
                    signalLongMap.get(previousIndex).getValue() == 0 && signalLongMap.get(index).getValue() > 0) {
                entryPrice = openMap.get(nextIndex).getValue();
                nbBars = 1;
                isOngoingTrade = true;
                direction = TradeType.LONG;
                dateEntry = nextIndex;
            }
        }

        results.computeStats();
        return results;
    }

}
