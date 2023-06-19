package com.robot.strategies;

import com.robot.timeframe.MultipleTimeFrameBarHolder;
import com.robots.utils.GlobalParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.rules.OverIndicatorRule;

import java.time.LocalDateTime;

public class BotTrader {

    private static final Logger LOG = LoggerFactory.getLogger(BotTrader.class);
    Strategy strategy;
    private MultipleTimeFrameBarHolder holder;

    public BotTrader() {

        holder = new MultipleTimeFrameBarHolder();
        strategy = buildStrategy(holder.getSerieHighTimeFrameBid(), holder.getSerieLowTimeFrameBid(), GlobalParams.PARAM_TYPE_1, GlobalParams.PARAM_TYPE_1);

    }

    protected void run() {
        int endIndex = holder.getSerieHighTimeFrameBid().getEndIndex();
        if (strategy.shouldEnter(endIndex)) {
            LOG.info("Should enter");
        }
    }

    protected Strategy buildStrategy(BarSeries serieHighTF, BarSeries serieLowTF, int[] paramHighTF, int[] paramLowTF) {

        // High timeframe
        ClosePriceIndicator closeHighTF = new ClosePriceIndicator(serieHighTF);
        RSIIndicator rsiHighTF = new RSIIndicator(closeHighTF, paramHighTF[0]);
        StochasticRSIIndicator stochRsiHighTF = new StochasticRSIIndicator(rsiHighTF, paramHighTF[1]);
        SMAIndicator smaFastHighTF = new SMAIndicator(stochRsiHighTF, paramHighTF[2]);
        SMAIndicator smaSlowHighTF = new SMAIndicator(smaFastHighTF, paramHighTF[3]);

        // Low timeframe
        ClosePriceIndicator closeLowTF = new ClosePriceIndicator(serieLowTF);
        RSIIndicator rsiLowTF = new RSIIndicator(closeLowTF, paramLowTF[0]);
        StochasticRSIIndicator stochRsiLowTF = new StochasticRSIIndicator(rsiLowTF, paramLowTF[1]);
        SMAIndicator smaFastLowTF = new SMAIndicator(stochRsiLowTF, paramLowTF[2]);
        SMAIndicator smaSlowLowTF = new SMAIndicator(smaFastLowTF, paramLowTF[3]);

        Rule entryRule = new OverIndicatorRule(smaFastHighTF, smaSlowHighTF)
                .and(new CrossedUpIndicatorRule(smaFastLowTF, smaSlowLowTF));

        Rule exitRule = new CrossedDownIndicatorRule(smaFastHighTF, smaSlowHighTF)
                .or(new CrossedDownIndicatorRule(smaFastLowTF, smaSlowLowTF));

        return new BaseStrategy(entryRule, exitRule, 100);

    }

    public void addAskBar(LocalDateTime dt, double open, double high, double low, double close) {
        holder.addAskBar(dt, open, high, low, close);
        run();
    }

    public void addBidBar(LocalDateTime dt, double open, double high, double low, double close) {
        holder.addBidBar(dt, open, high, low, close);
        run();
    }

}
