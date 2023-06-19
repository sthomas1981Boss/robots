package com.robot.strategies;

import com.robot.chart.TradingChart;
import com.robot.indicators.Indicator;
import com.robot.models.timeseries.OhlcTimeserie;
import com.robot.models.timeseries.Timeserie;
import com.robot.utils.CsvUtils;
import com.robot.utils.GlobalParams;
import com.robot.utils.TimeFrameUtils;

import java.util.List;

public class BackTest {

    @org.junit.jupiter.api.Test
    void run() throws InterruptedException {

        // Params
        int slow = 60;
        int[] paramsSlow = GlobalParams.PARAM_TYPE_1;
        int fast = 15;
        int[] paramsFast = GlobalParams.PARAM_TYPE_1;

        OhlcTimeserie ohlc = CsvUtils.readCsv("src/test/resources/data/data_1minute_week.csv", "EURUSD");
        OhlcTimeserie ohlcFast = TimeFrameUtils.convert(ohlc, fast);
        OhlcTimeserie ohlcSlow = TimeFrameUtils.convert(ohlc, slow);

        Timeserie closeFast = ohlcFast.getClose();
        Timeserie closeSlow = ohlcSlow.getClose();
        Timeserie closeSlowIndexedFast = TimeFrameUtils.reIndexAndFill(closeSlow, closeFast.getIndex());

        List<Timeserie> dtoscFast = Indicator.DTosc(closeFast, paramsFast[0], paramsFast[1], paramsFast[2], paramsFast[3]);

        List<Timeserie> dtoscSlow = Indicator.DTosc(closeSlow, paramsSlow[0], paramsSlow[1], paramsSlow[2], paramsSlow[3]);
        dtoscSlow = TimeFrameUtils.reIndexAndFill(dtoscSlow, closeFast.getIndex());

        TradingChart chart = new TradingChart();
        chart.show(ohlcFast, dtoscSlow, dtoscFast);
    }

}
