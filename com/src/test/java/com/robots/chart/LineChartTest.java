package com.robots.chart;


import com.robots.strategies.Indicator;
import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.timeseries.Timeserie;
import com.robots.utils.CsvUtils;
import com.robots.utils.GlobalParams;
import com.robots.utils.TimeFrameUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class LineChartTest {

    private Timeserie closeFilled;
    private Timeserie close;
    private Timeserie close15;
    private Timeserie close60;

    @BeforeEach
    void setUp() {
        OhlcTimeserie ts = CsvUtils.readCsv("src/test/resources/data/data_1minute_week.csv", "EURUSD");
        OhlcTimeserie ts15min = TimeFrameUtils.convert(ts, 15);
        OhlcTimeserie ts60min = TimeFrameUtils.convert(ts, 60);
        close = ts.getClose();
        close15 = ts15min.getClose();
        close60 = ts60min.getClose();
        closeFilled = TimeFrameUtils.reIndexAndFill(close60, close15.getIndex());
    }

    @Test
    void show() throws InterruptedException {
        LineChart chart = new LineChart();
        int[] paramsSlow = GlobalParams.PARAM_TYPE_1;
        List<Timeserie> dtosc = Indicator.DTosc(close15, paramsSlow[0], paramsSlow[1], paramsSlow[2], paramsSlow[3]);
        Timeserie rsi = Indicator.RSIWilderEma(close15, paramsSlow[0]);
        Timeserie stochRsi = Indicator.Stochastic(rsi, paramsSlow[1]);
        Timeserie smaF = Indicator.SMA(stochRsi, paramsSlow[2]);
        Timeserie smaS = Indicator.SMA(smaF, paramsSlow[3]);
        List<Timeserie> timeserieList = Arrays.asList(closeFilled, close15, close60);
        chart.show(smaS);
    }
}