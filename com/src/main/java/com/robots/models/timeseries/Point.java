package com.robots.models.timeseries;

import java.time.ZonedDateTime;

public class Point implements Comparable<Point> {

    private ZonedDateTime dt;
    private double value;


    public Point(ZonedDateTime dt, double value) {
        this.dt = dt;
        this.value = value;
    }

    public ZonedDateTime getDt() {
        return dt;
    }

    public double getValue() {
        return value;
    }

    @Override
    public int compareTo(Point o) {
        return this.dt.compareTo(o.getDt());
    }

}
