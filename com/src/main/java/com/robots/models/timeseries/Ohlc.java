package com.robots.models.timeseries;

import java.time.ZonedDateTime;

public class Ohlc implements Comparable<Ohlc> {

    private ZonedDateTime dt;
    private double open;
    private double high;
    private double low;
    private double close;

    public Ohlc(ZonedDateTime dt, double open, double high, double low, double close) {
        this.dt = dt;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public ZonedDateTime getDt() {
        return dt;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    @Override
    public int compareTo(Ohlc o) {
        return this.dt.compareTo(o.getDt());
    }

}
