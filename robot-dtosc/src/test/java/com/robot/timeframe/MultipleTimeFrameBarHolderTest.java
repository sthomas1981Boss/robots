package com.robot.timeframe;


import org.junit.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class MultipleTimeFrameBarHolderTest {

    @org.junit.jupiter.api.Test
    void addData() {

        MultipleTimeFrameBarHolder holder = new MultipleTimeFrameBarHolder();

        String csvFile = "src/test/resources/data/data_one_minute.csv";

        String line = "";
        String cvsSplitBy = ",";

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip header row
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] row = line.split(cvsSplitBy);

                LocalDateTime datetime = LocalDateTime.parse(row[0], dateFormatter);
                float open = Float.parseFloat(row[1]);
                float high = Float.parseFloat(row[2]);
                float low = Float.parseFloat(row[3]);
                float close = Float.parseFloat(row[4]);

                holder.addAskBar(datetime, open, high, low, close);
                holder.addBidBar(datetime, open, high, low, close);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(holder.getSerieHighTimeFrameAsk().getBar(3).getHighPrice().floatValue(), 1.12247f, 0.00001);
        Assert.assertEquals(holder.getSerieHighTimeFrameAsk().getBar(4).getLowPrice().floatValue(), 1.12186f, 0.00001);
        Assert.assertEquals(holder.getSerieLowTimeFrameAsk().getBar(3).getLowPrice().floatValue(), 1.12156f, 0.00001);

        Assert.assertEquals(holder.getSerieHighTimeFrameBid().getBar(3).getHighPrice().floatValue(), 1.12247f, 0.00001);
        Assert.assertEquals(holder.getSerieHighTimeFrameBid().getBar(4).getLowPrice().floatValue(), 1.12186f, 0.00001);
        Assert.assertEquals(holder.getSerieLowTimeFrameBid().getBar(3).getLowPrice().floatValue(), 1.12156f, 0.00001);

    }

}