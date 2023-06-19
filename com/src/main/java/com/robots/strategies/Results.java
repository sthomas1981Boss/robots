package com.robots.strategies;

import com.robots.models.timeseries.Point;
import com.robots.models.timeseries.Timeserie;
import com.robots.models.trades.Trade;
import com.robots.models.trades.TradeType;

import java.util.ArrayList;
import java.util.List;

public class Results {

    List<Trade> trades;
    private String params;
    private int nbTrades;

    private double profitFactor;

    private double averagePnl;

    private int nbTradesWinLong;

    private double averagePnlWinLong;

    private int nbTradesLossLong;

    private double averagePnlLossLong;

    private int nbTradesWinShort;

    private double averagePnlWinShort;

    private int nbTradesLossShort;

    private double averagePnlLossShort;

    private double maxDrawdown;

    private Timeserie equity;

    public Results() {
        this.trades = new ArrayList<Trade>();
        this.equity = new Timeserie("Equity");
    }

    public void computeStats() {

        this.nbTrades = this.trades.size();

        double avgLongPnl = 0;
        double avgLongPnlWin = 0;
        int nbLongWinTrade = 0;
        double avgLongPnlLoss = 0;
        int nbLongLossTrade = 0;

        for (Trade trade : this.trades) {
            /*
            if (trade.getTradeType() == TradeType.LONG) {
                avgLongPnl += trade.getPnl();
                if (trade.getPnl() >= 0) {
                    nbLongWinTrade += 1;
                    avgLongPnlWin += trade.getPnl();
                } else {
                    nbLongLossTrade += 1;
                    avgLongPnlLoss += trade.getPnl();
                }
            }*/
        }

        this.nbTradesLossLong = nbLongLossTrade;
        this.nbTradesWinLong = nbLongWinTrade;
        if (nbLongLossTrade != 0) {
            this.averagePnlLossLong = avgLongPnlLoss / nbLongLossTrade;
        }
        if (nbLongWinTrade != 0) {
            this.averagePnlWinLong = avgLongPnlWin / nbLongLossTrade;
        }

        this.profitFactor = (double) this.nbTradesWinLong / (double) this.nbTrades * 100;
        this.averagePnl = avgLongPnl / this.nbTrades;
    }

    public void addTrade(Trade trade) {
        this.trades.add(trade);
    }

    public void addEquity(Point p) {
        this.equity.add(p);
    }

    public int getNbTrades() {
        return nbTrades;
    }

    public void setNbTrades(int nbTrades) {
        this.nbTrades = nbTrades;
    }

    public double getProfitFactor() {
        return profitFactor;
    }

    public void setProfitFactor(double profitFactor) {
        this.profitFactor = profitFactor;
    }

    public double getAveragePnl() {
        return averagePnl;
    }

    public void setAveragePnl(double averagePnl) {
        this.averagePnl = averagePnl;
    }

    public int getNbTradesWinLong() {
        return nbTradesWinLong;
    }

    public void setNbTradesWinLong(int nbTradesWinLong) {
        this.nbTradesWinLong = nbTradesWinLong;
    }

    public double getAveragePnlWinLong() {
        return averagePnlWinLong;
    }

    public void setAveragePnlWinLong(double averagePnlWinLong) {
        this.averagePnlWinLong = averagePnlWinLong;
    }

    public int getNbTradesLossLong() {
        return nbTradesLossLong;
    }

    public void setNbTradesLossLong(int nbTradesLossLong) {
        this.nbTradesLossLong = nbTradesLossLong;
    }

    public double getAveragePnlLossLong() {
        return averagePnlLossLong;
    }

    public void setAveragePnlLossLong(double averagePnlLossLong) {
        this.averagePnlLossLong = averagePnlLossLong;
    }

    public int getNbTradesWinShort() {
        return nbTradesWinShort;
    }

    public void setNbTradesWinShort(int nbTradesWinShort) {
        this.nbTradesWinShort = nbTradesWinShort;
    }

    public double getAveragePnlWinShort() {
        return averagePnlWinShort;
    }

    public void setAveragePnlWinShort(double averagePnlWinShort) {
        this.averagePnlWinShort = averagePnlWinShort;
    }

    public int getNbTradesLossShort() {
        return nbTradesLossShort;
    }

    public void setNbTradesLossShort(int nbTradesLossShort) {
        this.nbTradesLossShort = nbTradesLossShort;
    }

    public double getAveragePnlLossShort() {
        return averagePnlLossShort;
    }

    public void setAveragePnlLossShort(double averagePnlLossShort) {
        this.averagePnlLossShort = averagePnlLossShort;
    }

    public double getMaxDrawdown() {
        return maxDrawdown;
    }

    public void setMaxDrawdown(double maxDrawdown) {
        this.maxDrawdown = maxDrawdown;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public Timeserie getEquity() {
        return equity;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Parameters                   : ").append(params).append("\n");
        sb.append("Nb trades                    : ").append(this.nbTrades).append("\n");
        sb.append("Average PNL                  : ").append(this.averagePnl).append("\n");
        sb.append("Profit factor                : ").append(this.profitFactor).append("\n");
        sb.append("Nb win long trades           : ").append(this.nbTradesWinLong).append("\n");
        sb.append("Average PNL win long trades  : ").append(this.averagePnlWinLong).append("\n");
        sb.append("Nb loss long trades          : ").append(this.nbTradesLossLong).append("\n");
        sb.append("Average PNL loss long trades : ").append(this.averagePnlLossLong).append("\n");
        return sb.toString();
    }
}
