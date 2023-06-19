package com.robots.models.timeseries;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.*;

public class Timeserie {

    private static final Logger LOG = LoggerFactory.getLogger(Timeserie.class);

    private List<Point> data;

    private String name;

    public Timeserie(String name) {
        this.data = new ArrayList<Point>();
        this.name = name;
    }

    public void add(ZonedDateTime dt, double value) {
        this.data.add(new Point(dt, value));
    }

    public void add(Point p) {
        this.data.add(p);
    }

    public void sort() {
        Collections.sort(this.data);
    }

    public List<Point> getData() {
        return data;
    }

    public List<ZonedDateTime> getIndex() {
        List<ZonedDateTime> index = new ArrayList<ZonedDateTime>();
        for (int i = 0; i < size(); i++) {
            index.add(get(i).getDt());
        }
        return index;
    }

    public Map<ZonedDateTime, Point> getMap() {
        Map<ZonedDateTime, Point> tsMap = new TreeMap<ZonedDateTime, Point>();
        for (int i = 0; i < size(); i++) {
            tsMap.put(get(i).getDt(), get(i));
        }
        return tsMap;
    }

    public Point get(int i) {
        return data.get(i);
    }

    public int size() {
        return data.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print() {
        for (int i = 0; i < size(); i++) {
            LOG.info("Date {} - Value {}", get(i).getDt().toString(), get(i).getValue());
        }
    }

}
