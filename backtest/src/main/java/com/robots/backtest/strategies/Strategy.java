package com.robots.backtest.strategies;

import com.robots.strategies.Results;
import com.robots.models.timeseries.OhlcTimeserie;

public class Strategy {

    protected OhlcTimeserie ohlc;

    protected Results results;

    public Strategy(OhlcTimeserie ohlc) {
        this.ohlc = ohlc;
    }

    public Results getResults() {
        return results;
    }

}
