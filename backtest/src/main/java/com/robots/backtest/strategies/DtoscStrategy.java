package com.robots.backtest.strategies;

import com.robots.backtest.engine.BacktestEngine;
import com.robots.strategies.Indicator;
import com.robots.strategies.Rule;
import com.robots.strategies.Results;
import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.timeseries.Timeserie;
import com.robots.utils.TimeFrameUtils;

import java.util.List;

public class DtoscStrategy implements Runnable {

    private OhlcTimeserie ohlc;

    private int slow;

    private int[] paramsSlow;

    private int fast;

    private int[] paramsFast;

    private Results results;

    public DtoscStrategy(OhlcTimeserie ohlc, int slow, int[] paramsSlow, int fast, int[] paramsFast) {
        this.ohlc = ohlc;
        this.slow = slow;
        this.paramsSlow = paramsSlow;
        this.fast = fast;
        this.paramsFast = paramsFast;
    }

    @Override
    public void run() {

        OhlcTimeserie ohlcFast = TimeFrameUtils.convert(ohlc, fast);
        OhlcTimeserie ohlcSlow = TimeFrameUtils.convert(ohlc, slow);
        Timeserie closeFast = ohlcFast.getClose();
        Timeserie closeSlow = ohlcSlow.getClose();

        // Compute indicator
        List<Timeserie> dtoscFast = Indicator.DTosc(closeFast, paramsFast[0], paramsFast[1], paramsFast[2], paramsFast[3]);
        List<Timeserie> dtoscSlow = Indicator.DTosc(closeSlow, paramsSlow[0], paramsSlow[1], paramsSlow[2], paramsSlow[3]);
        dtoscSlow = TimeFrameUtils.reIndexAndFill(dtoscSlow, closeFast.getIndex());

        // Compute signal
        Timeserie signalFast = Rule.OverRule(dtoscFast.get(0), dtoscFast.get(1));
        Timeserie signalSlow = Rule.OverRule(dtoscSlow.get(0), dtoscSlow.get(1));
        Timeserie signal = Rule.AndRule(signalFast, signalSlow);

        BacktestEngine engine = new BacktestEngine();
        results = engine.launch(ohlcFast, signal);

    }

    public Results getResults() {
        return results;
    }

}
