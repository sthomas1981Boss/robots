package com.robots.backtest;

import com.robots.backtest.strategies.DtoscStrategy;
import com.robots.strategies.Results;
import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.timeseries.Timeserie;
import com.robots.utils.CsvUtils;
import com.robots.utils.GlobalParams;
import com.robots.utils.TimeFrameUtils;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class LauncherDtoscBacktest {
    public static void main(String[] args) throws InterruptedException {

        String csvFilePath = "backtest/src/main/resources/data/data_backtest.csv";
        String currencyPair = "EURUSD";

        //int slow = 60;
        //int[] paramSlow = GlobalParams.PARAM_TYPE_1;
        //int fast = 15;
        //int[] paramFast = GlobalParams.PARAM_TYPE_1;

        // Reading csv file and convert to appropriate timefamre
        OhlcTimeserie ohlc = CsvUtils.readCsv(csvFilePath, currencyPair);

        for (int[] couple : Arrays.asList(new int[]{5,15}, new int[]{5,30}, new int[]{15,30}, new int[]{5,60}, new int[]{30,60}, new int[]{30,120}, new int[]{60,120}, new int[]{60,240})) {
            int slow = couple[1];
            int fast = couple[0];
            for (int[] paramSlow : Arrays.asList(GlobalParams.PARAM_TYPE_1, GlobalParams.PARAM_TYPE_2, GlobalParams.PARAM_TYPE_3, GlobalParams.PARAM_TYPE_4)) {
                for (int[] paramFast : Arrays.asList(GlobalParams.PARAM_TYPE_1, GlobalParams.PARAM_TYPE_2, GlobalParams.PARAM_TYPE_3, GlobalParams.PARAM_TYPE_4)) {

                    // Build string of param
                    StringBuilder sb = new StringBuilder();
                    sb.append("Slow: ").append(slow).append("min [")
                            .append(paramSlow[0]).append(",").append(paramSlow[1]).append(",").append(paramSlow[2]).append(",").append(paramSlow[3]).append("]")
                            .append(" - Fast: ").append(fast).append("min [")
                            .append(paramFast[0]).append(",").append(paramFast[1]).append(",").append(paramFast[2]).append(",").append(paramFast[3]).append("]");

                    // Convert to appropriate timefamre
                    OhlcTimeserie ohlcFast = TimeFrameUtils.convert(ohlc, fast);

                    // Launch backtest of strategy
                    DtoscStrategy strategy = new DtoscStrategy(ohlc, slow, paramSlow, fast, paramFast);
                    strategy.run();

                    Results results = strategy.getResults();
                    results.setParams(sb.toString());
                    Timeserie equity = results.getEquity();

                    // Print results
                    System.out.println(results.toString());

                }
            }
        }

        // Show
        /*TradingChart chart1 = new TradingChart();
        chart1.show(ohlcFast, equity);
        sleep(1000000);*/

    }
}