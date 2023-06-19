package com.robots.models.timeseries;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OhlcTimeserie {

    private List<Ohlc> data;

    private String name;

    public OhlcTimeserie(String name) {
        this.data = new ArrayList<Ohlc>();
        this.name = name;
    }

    public void add(ZonedDateTime dt, double open, double high, double low, double close) {
        this.data.add(new Ohlc(dt, open, high, low, close));
    }

    public void add(Ohlc ohlc) {
        this.data.add(ohlc);
    }

    public void sort() {
        Collections.sort(this.data);
    }

    public List<Ohlc> getData() {
        return data;
    }

    public List<ZonedDateTime> getIndex() {
        List<ZonedDateTime> index = new ArrayList<ZonedDateTime>();
        for (int i = 0; i < size(); i++) {
            index.add(get(i).getDt());
        }
        return index;
    }

    public Ohlc get(int i) {
        return data.get(i);
    }

    public int size() {
        return data.size();
    }

    public String getName() {
        return name;
    }

    public Timeserie getOpen() {
        Timeserie ts = new Timeserie(getName());
        for (int i = 0; i < size(); i++) {
            ts.add(get(i).getDt(), get(i).getOpen());
        }
        return ts;
    }

    public Timeserie getHigh() {
        Timeserie ts = new Timeserie(getName());
        for (int i = 0; i < size(); i++) {
            ts.add(get(i).getDt(), get(i).getHigh());
        }
        return ts;
    }

    public Timeserie getLow() {
        Timeserie ts = new Timeserie(getName());
        for (int i = 0; i < size(); i++) {
            ts.add(get(i).getDt(), get(i).getLow());
        }
        return ts;
    }

    public Timeserie getClose() {
        Timeserie ts = new Timeserie(getName());
        for (int i = 0; i < size(); i++) {
            ts.add(get(i).getDt(), get(i).getClose());
        }
        return ts;
    }

}
