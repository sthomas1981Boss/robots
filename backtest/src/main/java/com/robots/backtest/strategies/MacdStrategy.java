package com.robots.backtest.strategies;


// Article
//Take Profit and Stop Loss Trading Strategies Comparison in Combination with an MACD Trading System
// Dimitrios Vezeris 1,* , Themistoklis Kyrgos 2 and Christos Schinas 1

//Abstract: A lot of strategies for Take Profit and Stop Loss functionalities have been propounded
// and scrutinized over the years. In this paper, we examine various strategies added to a simple MACD automated
// trading system and used on selected assets from Forex, Metals, Energy, and Cryptocurrencies categories and
// afterwards, we compare and contrast their results. We conclude that Take Profit strategies based on faster take
// profit signals on MACD are not better than a simple MACD strategy and of the different Stop Loss strategies based
// on ATR, the sliding and variable ATR window has the best results for a period of 12 and a multiplier of 6.
// For the first time, to the best of our knowledge, we implement a combination of an adaptive MACD Expert Advisor
// that uses back-tested optimized parameters per asset with price levels defined by the ATR indicator,
// used to set limits for Stop Loss.


import com.robots.backtest.engine.SimpleTPEngine;
import com.robots.strategies.Indicator;
import com.robots.strategies.Rule;
import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.timeseries.Timeserie;

import java.util.List;

import static java.lang.Thread.sleep;

public class MacdStrategy extends Strategy implements Runnable {

    private int macdFast;
    private int macdSlow;
    private int macdSignal;
    private int macdTp;

    public MacdStrategy(OhlcTimeserie ohlc, int macdFast, int macdSlow, int macdSignal, int macdTp) {
        super(ohlc);
        this.macdFast = macdFast;
        this.macdSlow = macdSlow;
        this.macdSignal = macdSignal;
        this.macdTp = macdTp;
    }

    @Override
    public void run() {

        Timeserie close = ohlc.getClose();
        List<Timeserie> macd = Indicator.MACD(close, macdFast, macdSlow, macdSignal);

        // long signal
        Timeserie longSignal = Rule.OverRule(macd.get(0), macd.get(1));

        // take profit signal
        Timeserie tpLine = Indicator.EMA(macd.get(0), macdTp);
        Timeserie tpSignal = Rule.UnderRule(macd.get(0), tpLine);

        // stop loss signal
        Timeserie atr = Indicator.ATR(ohlc, 14);

        SimpleTPEngine engine = new SimpleTPEngine();
        results = engine.launch(ohlc, longSignal, tpSignal);

        /* Show
        try {

            List<Timeserie> tss = new ArrayList<Timeserie>();
            tss.add(macd.get(0));
            tss.add(tpLine);

            List<Timeserie> signals = new ArrayList<Timeserie>();
            signals.add(longSignal);
            signals.add(tpSignal);

            TradingChart chart1 = new TradingChart();
            chart1.show(ohlc, tss, signals, results.getEquity());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

    }

}
