package com.robots.utils;


import com.robots.models.timeseries.Ohlc;
import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.timeseries.Point;
import com.robots.models.timeseries.Timeserie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeFrameUtils {

    private static final Logger LOG = LoggerFactory.getLogger(TimeFrameUtils.class);

    public static OhlcTimeserie convert(OhlcTimeserie ts, int minutes) {
        OhlcTimeserie newTs = new OhlcTimeserie(minutes + " min");
        ZonedDateTime startTime = null;
        double open = 0;
        double high = Double.MIN_VALUE;
        double low = Double.MAX_VALUE;
        double close = 0;

        // Find the first hour met
        ZonedDateTime firstHourMet = ts.get(0).getDt().truncatedTo(ChronoUnit.HOURS).plusHours(1);

        for (Ohlc ohlc : ts.getData()) {
            ZonedDateTime currentTime = ohlc.getDt();
            double ohlcOpen = ohlc.getOpen();
            double ohlcHigh = ohlc.getHigh();
            double ohlcLow = ohlc.getLow();
            double ohlcClose = ohlc.getClose();

            // Check if the current interval should start
            if (startTime == null) {
                if (currentTime.equals(firstHourMet)) {
                    startTime = firstHourMet;
                } else {
                    continue;
                }
            }

            // Check if a new interval should be started
            if (currentTime.isAfter(startTime.plusMinutes(minutes - 1))) {
                // Add the previous interval's OHLC data
                if (startTime != null) {
                    newTs.add(startTime.plusMinutes(minutes), open, high, low, close);
                }

                // Start a new interval
                startTime = startTime.plusMinutes(minutes);
                open = ohlcOpen;
                high = ohlcHigh;
                low = ohlcLow;
                close = ohlcClose;
            } else {
                // Update high and low values
                high = Math.max(high, ohlcHigh);
                low = Math.min(low, ohlcLow);
                close = ohlcClose;
            }
        }

        return newTs;

    }

    public static List<Timeserie> reIndexAndFill(List<Timeserie> tsl, List<ZonedDateTime> lowerIndex) {
        List<Timeserie> newTs = new ArrayList<Timeserie>();
        for (Timeserie ts : tsl) {
            newTs.add(reIndexAndFill(ts, lowerIndex));
        }
        return (newTs);
    }

    public static Timeserie reIndexAndFill(Timeserie ts, List<ZonedDateTime> lowerIndex) {

        Timeserie newTs = new Timeserie(ts.getName());

        Map<ZonedDateTime, Point> tsMap = ts.getMap();
        ZonedDateTime firstDateMet = ts.get(0).getDt();
        double value = 0;

        for (int i = 1; i < lowerIndex.size(); i++) {

            if (newTs.size() == 0 && !firstDateMet.equals(lowerIndex.get(i))) {
                continue;
            }

            if (tsMap.containsKey(lowerIndex.get(i))) {
                value = tsMap.get(lowerIndex.get(i)).getValue();
                newTs.add(tsMap.get(lowerIndex.get(i)));
            } else {
                newTs.add(lowerIndex.get(i), value);
            }
        }

        return newTs;
    }

}
