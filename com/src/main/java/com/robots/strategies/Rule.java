package com.robots.strategies;

import com.robots.models.timeseries.Point;
import com.robots.models.timeseries.Timeserie;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class Rule {

    public static Timeserie CrossOver(Timeserie ts1, Timeserie ts2) {

        Timeserie signal = new Timeserie("Signal");

        Map<ZonedDateTime, Point> ts1Map = ts1.getMap();
        Map<ZonedDateTime, Point> ts2Map = ts2.getMap();
        List<ZonedDateTime> index = ts1.getIndex();

        for (int i = 1; i < ts1.size() ; i++) {
            Point p1 = ts1Map.get(index.get(i));
            Point p1Prev = ts1Map.get(index.get(i-1));
            Point p2 = ts2Map.get(index.get(i));
            Point p2Prev = ts2Map.get(index.get(i-1));

            if (p2Prev == null) {
                continue;
            }

            if (p1Prev.getValue() < p2Prev.getValue() &&
            p1.getValue() >= p2.getValue()) {
                signal.add(ts1.get(i).getDt(), 1);
            } else {
                signal.add(ts1.get(i).getDt(), 0);
            }

        }

        return signal;
    }

    public static Timeserie CrossDown(Timeserie ts1, Timeserie ts2) {

        Timeserie signal = new Timeserie("Signal");

        Map<ZonedDateTime, Point> ts1Map = ts1.getMap();
        Map<ZonedDateTime, Point> ts2Map = ts2.getMap();
        List<ZonedDateTime> index = ts1.getIndex();

        for (int i = 1; i < ts1.size() ; i++) {
            Point p1 = ts1Map.get(index.get(i));
            Point p1Prev = ts1Map.get(index.get(i-1));
            Point p2 = ts2Map.get(index.get(i));
            Point p2Prev = ts2Map.get(index.get(i-1));

            if (p2Prev == null) {
                continue;
            }

            if (p1Prev.getValue() > p2Prev.getValue() &&
                    p1.getValue() <= p2.getValue()) {
                signal.add(ts1.get(i).getDt(), 1);
            } else {
                signal.add(ts1.get(i).getDt(), 0);
            }

        }

        return signal;
    }

    public static Timeserie OverRule(Timeserie ts1, Timeserie ts2) {

        Timeserie signal = new Timeserie("Signal");

        Map<ZonedDateTime, Point> ts1Map = ts1.getMap();
        Map<ZonedDateTime, Point> ts2Map = ts2.getMap();
        List<ZonedDateTime> index = ts1.getIndex();

        for (ZonedDateTime dt : index) {
            Point p1 = ts1Map.get(dt);
            Point p2 = ts2Map.get(dt);

            if (p2 == null) {
                continue;
            }

            if (p1.getValue() >= p2.getValue()) {
                signal.add(dt, 1);
            } else {
                signal.add(dt, 0);
            }
        }

        return signal;

    }

    public static Timeserie OverStrictRule(Timeserie ts1, Timeserie ts2) {

        Timeserie signal = new Timeserie("Signal");

        Map<ZonedDateTime, Point> ts1Map = ts1.getMap();
        Map<ZonedDateTime, Point> ts2Map = ts2.getMap();
        List<ZonedDateTime> index = ts1.getIndex();

        for (ZonedDateTime dt : index) {
            Point p1 = ts1Map.get(dt);
            Point p2 = ts2Map.get(dt);

            if (p2 == null) {
                continue;
            }

            if (p1.getValue() > p2.getValue()) {
                signal.add(dt, 1);
            } else {
                signal.add(dt, 0);
            }
        }

        return signal;

    }

    public static Timeserie UnderRule(Timeserie ts1, Timeserie ts2) {

        Timeserie signal = new Timeserie("Signal");

        Map<ZonedDateTime, Point> ts1Map = ts1.getMap();
        Map<ZonedDateTime, Point> ts2Map = ts2.getMap();
        List<ZonedDateTime> index = ts1.getIndex();

        for (ZonedDateTime dt : index) {
            Point p1 = ts1Map.get(dt);
            Point p2 = ts2Map.get(dt);

            if (p2 == null) {
                continue;
            }

            if (p1.getValue() <= p2.getValue()) {
                signal.add(dt, 1);
            } else {
                signal.add(dt, 0);
            }
        }

        return signal;

    }

    public static Timeserie UnderStrictRule(Timeserie ts1, Timeserie ts2) {

        Timeserie signal = new Timeserie("Signal");

        Map<ZonedDateTime, Point> ts1Map = ts1.getMap();
        Map<ZonedDateTime, Point> ts2Map = ts2.getMap();
        List<ZonedDateTime> index = ts1.getIndex();

        for (ZonedDateTime dt : index) {
            Point p1 = ts1Map.get(dt);
            Point p2 = ts2Map.get(dt);

            if (p2 == null) {
                continue;
            }

            if (p1.getValue() < p2.getValue()) {
                signal.add(dt, 1);
            } else {
                signal.add(dt, 0);
            }
        }

        return signal;

    }

    public static Timeserie AndRule(Timeserie ts1, Timeserie ts2) {

        Timeserie signal = new Timeserie("Signal");

        Map<ZonedDateTime, Point> ts1Map = ts1.getMap();
        Map<ZonedDateTime, Point> ts2Map = ts2.getMap();
        List<ZonedDateTime> index = ts1.getIndex();

        for (ZonedDateTime dt : index) {
            Point p1 = ts1Map.get(dt);
            Point p2 = ts2Map.get(dt);

            if (p2 == null) {
                continue;
            }

            if ((p1.getValue() == p2.getValue()) && p1.getValue() == 1) {
                signal.add(dt, 1);
            } else {
                signal.add(dt, 0);
            }
        }

        return signal;

    }

}
