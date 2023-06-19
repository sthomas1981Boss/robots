package com.robots.backtest;

import com.robots.backtest.strategies.MacdStrategy;
import com.robots.chart.TradingChart;
import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.timeseries.Timeserie;
import com.robots.utils.CsvUtils;
import com.robots.utils.TimeFrameUtils;

import static java.lang.Thread.sleep;

public class LauncherMACDBacktest {
    public static void main(String[] args) throws InterruptedException {

        String csvFilePath = "backtest/src/main/resources/data/data_1week_1minute.csv";
        String currencyPair = "EURUSD";

        // Reading csv file and convert to appropriate timefamre
        OhlcTimeserie data = CsvUtils.readCsv(csvFilePath, currencyPair);
        OhlcTimeserie ohlc = TimeFrameUtils.convert(data, 15);

        MacdStrategy macd = new MacdStrategy(ohlc, 12, 26, 9, 3);
        macd.run();
        Timeserie equity = macd.getResults().getEquity();

        // Show
        TradingChart chart1 = new TradingChart();
        chart1.show(ohlc, equity);
        sleep(1000000);

        System.out.println(macd.getResults().toString());
    }
}