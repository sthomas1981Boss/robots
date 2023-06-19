package com.robot.timeframe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.LowestValueIndicator;

import java.time.LocalDateTime;

public class MultipleTimeFrameBarHolder {

    private static final Logger LOG = LoggerFactory.getLogger(MultipleTimeFrameBarHolder.class);

    private BarSeries serieMinuteAsk;

    private BarSeries serieMinuteBid;

    private BarSeries serieHighTimeFrameAsk;

    private BarSeries serieLowTimeFrameAsk;

    private BarSeries serieHighTimeFrameBid;

    private BarSeries serieLowTimeFrameBid;

    private BarSeries serieHighTimeFrameAskBuffer;

    private BarSeries serieLowTimeFrameAskBuffer;

    private BarSeries serieHighTimeFrameBidBuffer;

    private BarSeries serieLowTimeFrameBidBuffer;


    public MultipleTimeFrameBarHolder() {

        serieMinuteAsk = new BaseBarSeriesBuilder().withName("1minute_ask").build();
        serieMinuteAsk.setMaximumBarCount(GlobalParams.MAX_BAR_COUNT_MINUTE);
        serieMinuteBid = new BaseBarSeriesBuilder().withName("1minute_bid").build();
        serieMinuteBid.setMaximumBarCount(GlobalParams.MAX_BAR_COUNT_MINUTE);

        serieHighTimeFrameAsk = new BaseBarSeriesBuilder().withName("hf_ask").build();
        serieHighTimeFrameAsk.setMaximumBarCount(GlobalParams.MAX_BAR_COUNT_HOUR);
        serieHighTimeFrameAskBuffer = new BaseBarSeriesBuilder().withName("hf_ask_buffer").build();

        serieLowTimeFrameAsk = new BaseBarSeriesBuilder().withName("lf_ask").build();
        serieLowTimeFrameAsk.setMaximumBarCount(GlobalParams.MAX_BAR_COUNT_15_MINUTES);
        serieLowTimeFrameAskBuffer = new BaseBarSeriesBuilder().withName("lf_ask_buffer").build();

        serieHighTimeFrameBid = new BaseBarSeriesBuilder().withName("hf_bid").build();
        serieHighTimeFrameBid.setMaximumBarCount(GlobalParams.MAX_BAR_COUNT_HOUR);
        serieHighTimeFrameBidBuffer = new BaseBarSeriesBuilder().withName("hf_bid_buffer").build();

        serieLowTimeFrameBid = new BaseBarSeriesBuilder().withName("lf_bid").build();
        serieLowTimeFrameBid.setMaximumBarCount(GlobalParams.MAX_BAR_COUNT_15_MINUTES);
        serieLowTimeFrameBidBuffer = new BaseBarSeriesBuilder().withName("lf_bid_buffer").build();
    }

    public void addAskBar(LocalDateTime dt, double open, double high, double low, double close) {
        // LOG.info("Get ask bar {} ", String.format("%s | O: %.5f | H: %.5f | L: %.5f | C: %.5f ",
        //        dt.toString(), open, high, low, close));

        serieMinuteAsk.addBar(DatetimeUtils.toZoneDt(dt), open, high, low, close);

        serieLowTimeFrameAskBuffer.addBar(DatetimeUtils.toZoneDt(dt), open, high, low, close);
        serieHighTimeFrameAskBuffer.addBar(DatetimeUtils.toZoneDt(dt), open, high, low, close);

        // if it is hour, put in high data frame and low data frame
        if (dt.getMinute() == 0) {

            // 1 hour

            HighPriceIndicator highPrice = new HighPriceIndicator(serieHighTimeFrameAskBuffer);
            HighestValueIndicator highest = new HighestValueIndicator(highPrice, highPrice.getBarSeries().getBarCount());

            LowPriceIndicator lowPrice = new LowPriceIndicator(serieHighTimeFrameAskBuffer);
            LowestValueIndicator lowest = new LowestValueIndicator(lowPrice, lowPrice.getBarSeries().getBarCount());

            serieHighTimeFrameAsk.addBar(DatetimeUtils.toZoneDt(dt),
                    serieHighTimeFrameAskBuffer.getFirstBar().getOpenPrice().floatValue(),
                    highest.getValue(highest.getBarSeries().getBarCount() - 1).floatValue(),
                    lowest.getValue(lowest.getBarSeries().getBarCount() - 1).floatValue(),
                    serieHighTimeFrameAskBuffer.getLastBar().getClosePrice().floatValue());

            serieHighTimeFrameAskBuffer = new BaseBarSeriesBuilder().withName("hf_ask").build();

        }

        // if it is 15 min, put in low data frame
        if (dt.getMinute() % 15 == 0) {

            HighPriceIndicator highPrice = new HighPriceIndicator(serieLowTimeFrameAskBuffer);
            HighestValueIndicator highest = new HighestValueIndicator(highPrice, highPrice.getBarSeries().getBarCount());

            LowPriceIndicator lowPrice = new LowPriceIndicator(serieLowTimeFrameAskBuffer);
            LowestValueIndicator lowest = new LowestValueIndicator(lowPrice, lowPrice.getBarSeries().getBarCount());

            serieLowTimeFrameAsk.addBar(DatetimeUtils.toZoneDt(dt),
                    serieLowTimeFrameAskBuffer.getFirstBar().getOpenPrice().floatValue(),
                    highest.getValue(highest.getBarSeries().getBarCount() - 1).floatValue(),
                    lowest.getValue(lowest.getBarSeries().getBarCount() - 1).floatValue(),
                    serieLowTimeFrameAskBuffer.getLastBar().getClosePrice().floatValue());

            serieLowTimeFrameAskBuffer = new BaseBarSeriesBuilder().withName("lf_ask").build();

        }

    }

    public void addBidBar(LocalDateTime dt, double open, double high, double low, double close) {
        // LOG.info("Get bid bar {} ", String.format("%s | O: %.5f | H: %.5f | L: %.5f | C: %.5f ",
        //        dt.toString(), open, high, low, close));

        serieMinuteBid.addBar(DatetimeUtils.toZoneDt(dt), open, high, low, close);

        serieLowTimeFrameBidBuffer.addBar(DatetimeUtils.toZoneDt(dt), open, high, low, close);
        serieHighTimeFrameBidBuffer.addBar(DatetimeUtils.toZoneDt(dt), open, high, low, close);

        // if it is hour, put in high data frame and low data frame
        if (dt.getMinute() == 0) {

            // 1 hour

            HighPriceIndicator highPrice = new HighPriceIndicator(serieHighTimeFrameBidBuffer);
            HighestValueIndicator highest = new HighestValueIndicator(highPrice, highPrice.getBarSeries().getBarCount());

            LowPriceIndicator lowPrice = new LowPriceIndicator(serieHighTimeFrameBidBuffer);
            LowestValueIndicator lowest = new LowestValueIndicator(lowPrice, lowPrice.getBarSeries().getBarCount());

            serieHighTimeFrameBid.addBar(DatetimeUtils.toZoneDt(dt),
                    serieHighTimeFrameBidBuffer.getFirstBar().getOpenPrice().floatValue(),
                    highest.getValue(highest.getBarSeries().getBarCount() - 1).floatValue(),
                    lowest.getValue(lowest.getBarSeries().getBarCount() - 1).floatValue(),
                    serieHighTimeFrameBidBuffer.getLastBar().getClosePrice().floatValue());

            serieHighTimeFrameBidBuffer = new BaseBarSeriesBuilder().withName("hf_bid").build();

        }

        // if it is 15 min, put in low data frame
        if (dt.getMinute() % 15 == 0) {

            HighPriceIndicator highPrice = new HighPriceIndicator(serieLowTimeFrameBidBuffer);
            HighestValueIndicator highest = new HighestValueIndicator(highPrice, highPrice.getBarSeries().getBarCount());

            LowPriceIndicator lowPrice = new LowPriceIndicator(serieLowTimeFrameBidBuffer);
            LowestValueIndicator lowest = new LowestValueIndicator(lowPrice, lowPrice.getBarSeries().getBarCount());

            serieLowTimeFrameBid.addBar(DatetimeUtils.toZoneDt(dt),
                    serieLowTimeFrameBidBuffer.getFirstBar().getOpenPrice().floatValue(),
                    highest.getValue(highest.getBarSeries().getBarCount() - 1).floatValue(),
                    lowest.getValue(lowest.getBarSeries().getBarCount() - 1).floatValue(),
                    serieLowTimeFrameBidBuffer.getLastBar().getClosePrice().floatValue());

            serieLowTimeFrameBidBuffer = new BaseBarSeriesBuilder().withName("lf_bid").build();

        }

    }

    public BarSeries getSerieHighTimeFrameAsk() {
        return serieHighTimeFrameAsk;
    }

    public BarSeries getSerieLowTimeFrameAsk() {
        return serieLowTimeFrameAsk;
    }

    public BarSeries getSerieHighTimeFrameBid() {
        return serieHighTimeFrameBid;
    }

    public BarSeries getSerieLowTimeFrameBid() {
        return serieLowTimeFrameBid;
    }

    public BarSeries getSerieMinuteAsk() {
        return serieMinuteAsk;
    }

    public BarSeries getSerieMinuteBid() {
        return serieMinuteBid;
    }
}
