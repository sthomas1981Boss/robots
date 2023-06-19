package com.robot.strategies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class BotTraderTest {

    @org.junit.jupiter.api.Test
    void addData() {


        BotTrader botTrader = new BotTrader();

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

                botTrader.addAskBar(datetime, open, high, low, close);
                botTrader.addBidBar(datetime, open, high, low, close);

                botTrader.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}