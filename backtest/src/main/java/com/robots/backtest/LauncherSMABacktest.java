package com.robots.backtest;

import com.robots.backtest.strategies.SMAStrategy;
import com.robots.chart.TradingChart;
import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.timeseries.Timeserie;
import com.robots.strategies.Strategy;
import com.robots.utils.CsvUtils;
import com.robots.utils.TimeFrameUtils;

import static java.lang.Thread.sleep;

public class LauncherSMABacktest {

    public static void main(String[] args) throws InterruptedException {

        String csvFilePath = "backtest/src/main/resources/data/mid/data_1week_1minute.csv";
        String currencyPair = "EURUSD";

        // Reading csv file and convert to appropriate timefamre
        OhlcTimeserie data = CsvUtils.readCsv(csvFilePath, currencyPair);
        OhlcTimeserie ohlc = TimeFrameUtils.convert(data, 15);

        Strategy sma = new SMAStrategy();
        Engine engine = new Engine(10000, sma, ohlc, ohlc);
        engine.run();

        try {
            // Show
            TradingChart chart1 = new TradingChart();
            chart1.show(ohlc, engine.getResults().getEquity());
            sleep(1000000);
        } catch (Exception ex) {

        }
    }



}
