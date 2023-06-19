package com.robots.chart;


import com.robots.models.timeseries.Ohlc;
import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.timeseries.Point;
import com.robots.models.timeseries.Timeserie;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.OHLCDataset;

import java.util.Date;
import java.util.List;

public class Chart {

    protected JFreeChart chart;

    protected TimeSeriesCollection convert(Timeserie ts) {

        TimeSeriesCollection collection = new TimeSeriesCollection();
        TimeSeries timeSeries = new TimeSeries(ts.getName());

        for (int i = 0; i < ts.size(); i++) {
            Point point = ts.get(i);
            Second time = new Second(new Date(point.getDt().toEpochSecond() * 1000L));
            timeSeries.add(time, point.getValue());
        }

        collection.addSeries(timeSeries);
        return collection;
    }

    protected TimeSeriesCollection convert(List<Timeserie> ts) {

        TimeSeriesCollection collection = new TimeSeriesCollection();

        for (int i = 0; i < ts.size(); i++) {
            TimeSeries timeSeries = new TimeSeries(ts.get(i).getName());

            for (int j = 0; j < ts.get(i).size(); j++) {
                Point point = ts.get(i).get(j);
                Second time = new Second(new Date(point.getDt().toEpochSecond() * 1000L));
                timeSeries.add(time, point.getValue());
            }

            collection.addSeries(timeSeries);
        }
        return collection;
    }

    protected OHLCDataset convert(OhlcTimeserie series) {

        final int nbBars = series.size();

        Date[] dates = new Date[nbBars];
        double[] opens = new double[nbBars];
        double[] highs = new double[nbBars];
        double[] lows = new double[nbBars];
        double[] closes = new double[nbBars];
        double[] volumes = new double[nbBars];

        for (int i = 0; i < nbBars; i++) {
            Ohlc bar = series.get(i);
            dates[i] = new Date(bar.getDt().toEpochSecond() * 1000);
            opens[i] = bar.getOpen();
            highs[i] = bar.getHigh();
            lows[i] = bar.getLow();
            closes[i] = bar.getClose();
        }

        return new DefaultHighLowDataset(series.getName(), dates, highs, lows, opens, closes, volumes);

    }
}
