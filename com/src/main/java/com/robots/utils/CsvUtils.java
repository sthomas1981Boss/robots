package com.robots.utils;


import com.robots.models.timeseries.OhlcTimeserie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CsvUtils {

    public static OhlcTimeserie readCsv(String path, String name) {

        String line = "";
        String cvsSplitBy = ",";

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        OhlcTimeserie ts = new OhlcTimeserie(name);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // Skip header row
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] row = line.split(cvsSplitBy);

                ZonedDateTime datetime = DatetimeUtils.toZoneDt(row[0]);
                double open = Double.parseDouble(row[1]);
                double high = Double.parseDouble(row[2]);
                double low = Double.parseDouble(row[3]);
                double close = Double.parseDouble(row[4]);

                ts.add(datetime, open, high, low, close);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ts.sort();
        return ts;

    }

}
