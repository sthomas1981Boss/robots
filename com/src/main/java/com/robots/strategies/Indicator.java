package com.robots.strategies;


import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.timeseries.Point;
import com.robots.models.timeseries.Timeserie;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Indicator {

    public static Timeserie RSI(Timeserie ts, int period) {

        Timeserie rsi = new Timeserie(ts.getName());

        for (int i = period; i < ts.size(); i++) {
            double sumGain = 0;
            double sumLoss = 0;

            // Calculate average gain and average loss
            for (int j = i - period + 1; j <= i; j++) {
                double change = ts.get(j).getValue() - ts.get(j - 1).getValue();
                if (change >= 0) {
                    sumGain += change;
                } else {
                    sumLoss -= change;
                }
            }

            double avgGain = sumGain / period;
            double avgLoss = sumLoss / period;

            // Calculate RSI value
            double rs = avgGain / avgLoss;
            double rsiValue = 100 - (100 / (1 + rs));

            rsi.add(ts.get(i).getDt(), rsiValue);
        }

        return rsi;
    }

    public static Timeserie RSIWilderEma(Timeserie ts, int period) {
        Timeserie rsi = new Timeserie(ts.getName());

        double sumGain = 0;
        double sumLoss = 0;

        // Calculate initial gain and loss for the first period
        for (int i = 1; i <= period; i++) {
            double change = ts.get(i).getValue() - ts.get(i - 1).getValue();
            if (change >= 0) {
                sumGain += change;
            } else {
                sumLoss -= change;
            }
        }

        // Calculate initial average gain and average loss
        double avgGain = sumGain / period;
        double avgLoss = sumLoss / period;

        // Calculate initial RSI value
        double rs = avgGain / avgLoss;
        double rsiValue = 100 - (100 / (1 + rs));

        rsi.add(ts.get(period).getDt(), rsiValue);

        // Calculate RSI for the remaining values
        for (int i = period + 1; i < ts.size(); i++) {
            double change = ts.get(i).getValue() - ts.get(i - 1).getValue();
            double gain = (change >= 0) ? change : 0;
            double loss = (change < 0) ? -change : 0;

            avgGain = (avgGain * (period - 1) + gain) / period;
            avgLoss = (avgLoss * (period - 1) + loss) / period;

            rs = avgGain / avgLoss;
            rsiValue = 100 - (100 / (1 + rs));

            rsi.add(ts.get(i).getDt(), rsiValue);
        }

        return rsi;
    }

    public static Timeserie Stochastic(Timeserie ts, int period) {

        Timeserie stochastic = new Timeserie(ts.getName());

        for (int i = period; i < ts.size(); i++) {
            double max = ts.get(i - period + 1).getValue();
            double min = ts.get(i - period + 1).getValue();
            for (int j = i - period + 1; j <= i; j++) {
                if (ts.get(j).getValue() > max) {
                    max = ts.get(j).getValue();
                }
                if (ts.get(j).getValue() < min) {
                    min = ts.get(j).getValue();
                }
            }
            double stoch = 100 * (ts.get(i).getValue() - min) / (max - min);
            stochastic.add(ts.get(i).getDt(), stoch);
        }
        return stochastic;
    }

    public static Timeserie SMA(Timeserie ts, int period) {

        Timeserie sma = new Timeserie(ts.getName());

        for (int i = period; i < ts.size(); i++) {
            double sum = 0;
            for (int j = i - period + 1; j <= i; j++) {
                sum += ts.get(j).getValue();
            }
            double sumavg = sum / period;
            sma.add(ts.get(i).getDt(), sumavg);
        }

        return sma;

    }

    public static Timeserie EMA(Timeserie ts, int period) {

        Timeserie ema = new Timeserie(ts.getName());

        // Calculate the initial SMA as the first value in the series
        double sum = 0;
        for (int i = 0; i < period; i++) {
            sum += ts.get(i).getValue();
        }
        double smaInitial = sum / period;
        ema.add(ts.get(period - 1).getDt(), smaInitial);

        // Calculate EMA for the remaining values
        for (int i = period; i < ts.size(); i++) {
            double multiplier = 2.0 / (period + 1);
            double emaValue = (ts.get(i).getValue() - ema.get(i - period).getValue()) * multiplier + ema.get(i - period).getValue();
            ema.add(ts.get(i).getDt(), emaValue);
        }

        return ema;

    }

    public static List<Timeserie> MACD(Timeserie ts, int pfast, int pslow, int psignal) {
        List<Timeserie> macd = new ArrayList<Timeserie>();

        Timeserie emaFast = EMA(ts, pfast);
        Timeserie emaSlow = EMA(ts, pslow);

        Map<ZonedDateTime, Point> emaFastMap = emaFast.getMap();
        Map<ZonedDateTime, Point> emaSlowMap = emaSlow.getMap();

        Timeserie macdLine = new Timeserie("MACD Line");
        for (ZonedDateTime dt : emaSlow.getIndex()) {
            double value = emaFastMap.get(dt).getValue() - emaSlowMap.get(dt).getValue();
            macdLine.add(new Point(dt, value));
        }

        Timeserie signal = EMA(macdLine, psignal);
        signal.setName("Signal Line");

        macd.add(macdLine);
        macd.add(signal);

        return macd;

    }

    public static Timeserie TR(OhlcTimeserie data) {
        Timeserie trueRange = new Timeserie("TR");
        for (int i = 1; i < data.size(); i++) {
            double trueHigh = Math.max(data.get(i).getHigh(), data.get(i - 1).getClose());
            double trueLow = Math.min(data.get(i).getLow(), data.get(i - 1).getClose());
            trueRange.add(new Point(data.get(i).getDt(), trueHigh - trueLow));
        }
        return trueRange;
    }

    public static Timeserie ATR(OhlcTimeserie data, int period) {
        Timeserie avgTrueRange = EMA(TR(data), period);
        avgTrueRange.setName("ATR");
        return avgTrueRange;
    }

    public static List<Timeserie> DTosc(Timeserie ts, int rsiPeriod, int stochasticPeriod, int smaFast, int smaSlow) {

        List<Timeserie> smas = new ArrayList<Timeserie>();

        Timeserie rsi = RSIWilderEma(ts, rsiPeriod);
        Timeserie stochRsi = Stochastic(rsi, stochasticPeriod);
        Timeserie smaF = SMA(stochRsi, smaFast);
        Timeserie smaS = SMA(smaF, smaSlow);

        smas.add(smaF);
        smas.add(smaS);

        return smas;
    }

}
