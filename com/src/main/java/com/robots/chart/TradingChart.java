package com.robots.chart;


import com.robots.models.timeseries.OhlcTimeserie;
import com.robots.models.timeseries.Timeserie;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.OHLCDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TradingChart extends Chart {

    public void show(OhlcTimeserie ohlc, List<Timeserie> slow, List<Timeserie> fast, Timeserie signal) throws InterruptedException {
        Thread chartThread = new Thread(() -> {
            OHLCDataset ohlcDataset = convert(ohlc);
            TimeSeriesCollection slowTSC = convert(slow);
            TimeSeriesCollection fastTSC = convert(fast);
            TimeSeriesCollection signalTSC = convert(signal);

            // Candlestick rendering
            ValueAxis timeAxis = new DateAxis("Time");
            NumberAxis valueAxis = new NumberAxis(ohlc.getName());
            CandlestickRenderer renderer = new CandlestickRenderer();
            renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
            XYPlot plot = new XYPlot(ohlcDataset, (ValueAxis) null, valueAxis, renderer);

            CombinedDomainXYPlot combinedDomainPlot = new CombinedDomainXYPlot(timeAxis);
            combinedDomainPlot.add(plot, 3);
            valueAxis.setAutoRangeIncludesZero(false);
            renderer.setAutoWidthMethod(1);
            renderer.setDrawVolume(false);
            renderer.setDefaultItemLabelsVisible(false);

            // Indicator 1
            XYLineAndShapeRenderer indicatorRenderer = new XYLineAndShapeRenderer();
            indicatorRenderer.setSeriesShapesVisible(0, false);
            indicatorRenderer.setSeriesShapesVisible(1, false);
            indicatorRenderer.setSeriesPaint(0, Color.red);
            indicatorRenderer.setSeriesPaint(1, Color.blue);
            XYPlot indicator1Plot = new XYPlot(slowTSC, null, new NumberAxis(slow.get(0).getName()), indicatorRenderer);
            combinedDomainPlot.add(indicator1Plot, 1);

            // Indicator 2
            XYPlot indicator2Plot = new XYPlot(fastTSC, null, new NumberAxis(fast.get(0).getName()), indicatorRenderer);
            combinedDomainPlot.add(indicator2Plot, 1);

            // Signal
            XYLineAndShapeRenderer signalRenderer = new XYLineAndShapeRenderer();
            signalRenderer.setSeriesShapesVisible(0, false);
            signalRenderer.setSeriesPaint(0, Color.red);
            XYPlot indicator3Plot = new XYPlot(signalTSC, null, new NumberAxis(signal.getName()), signalRenderer);
            combinedDomainPlot.add(indicator3Plot, 1);

            // Create main chart
            this.chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, combinedDomainPlot, true);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setFillZoomRectangle(true);
            chartPanel.setMouseWheelEnabled(true);
            chartPanel.setPreferredSize(new Dimension(1480, 1020));

            // Application frame
            ApplicationFrame frame = new ApplicationFrame("");
            frame.setContentPane(chartPanel);
            frame.pack();
            UIUtils.centerFrameOnScreen(frame);
            frame.setVisible(true);
        });
        // Start the chart thread
        SwingUtilities.invokeLater(chartThread);
    }

    public void show(OhlcTimeserie ohlc, List<Timeserie> slow) throws InterruptedException {
        Thread chartThread = new Thread(() -> {
            OHLCDataset ohlcDataset = convert(ohlc);
            TimeSeriesCollection slowTSC = convert(slow);

            // Candlestick rendering
            ValueAxis timeAxis = new DateAxis("Time");
            NumberAxis valueAxis = new NumberAxis(ohlc.getName());
            CandlestickRenderer renderer = new CandlestickRenderer();
            renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
            XYPlot plot = new XYPlot(ohlcDataset, (ValueAxis) null, valueAxis, renderer);

            CombinedDomainXYPlot combinedDomainPlot = new CombinedDomainXYPlot(timeAxis);
            combinedDomainPlot.add(plot, 3);
            valueAxis.setAutoRangeIncludesZero(false);
            renderer.setAutoWidthMethod(1);
            renderer.setDrawVolume(false);
            renderer.setDefaultItemLabelsVisible(false);

            // Indicator 1
            XYLineAndShapeRenderer indicatorRenderer = new XYLineAndShapeRenderer();
            indicatorRenderer.setSeriesShapesVisible(0, false);
            indicatorRenderer.setSeriesShapesVisible(1, false);
            indicatorRenderer.setSeriesShapesVisible(2, false);
            indicatorRenderer.setSeriesPaint(0, Color.red);
            indicatorRenderer.setSeriesPaint(1, Color.blue);
            indicatorRenderer.setSeriesPaint(2, Color.green);
            XYPlot indicator1Plot = new XYPlot(slowTSC, null, new NumberAxis(), indicatorRenderer);
            combinedDomainPlot.add(indicator1Plot, 1);

            // Create main chart
            this.chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, combinedDomainPlot, true);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setFillZoomRectangle(true);
            chartPanel.setMouseWheelEnabled(true);
            chartPanel.setPreferredSize(new Dimension(1480, 1020));

            // Application frame
            ApplicationFrame frame = new ApplicationFrame("");
            frame.setContentPane(chartPanel);
            frame.pack();
            UIUtils.centerFrameOnScreen(frame);
            frame.setVisible(true);
        });
        // Start the chart thread
        SwingUtilities.invokeLater(chartThread);
    }

    public void show(OhlcTimeserie ohlc, Timeserie equity) throws InterruptedException {
        Thread chartThread = new Thread(() -> {
            OHLCDataset ohlcDataset = convert(ohlc);
            TimeSeriesCollection equityTSC = convert(equity);

            // Candlestick rendering
            ValueAxis timeAxis = new DateAxis("Time");
            NumberAxis valueAxis = new NumberAxis(ohlc.getName());
            CandlestickRenderer renderer = new CandlestickRenderer();
            renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
            XYPlot plot = new XYPlot(ohlcDataset,  null, valueAxis, renderer);

            CombinedDomainXYPlot combinedDomainPlot = new CombinedDomainXYPlot(timeAxis);
            combinedDomainPlot.add(plot, 3);
            valueAxis.setAutoRangeIncludesZero(false);
            renderer.setAutoWidthMethod(1);
            renderer.setDrawVolume(false);
            renderer.setDefaultItemLabelsVisible(false);

            // Signal
            XYLineAndShapeRenderer signalRenderer = new XYLineAndShapeRenderer();
            signalRenderer.setSeriesShapesVisible(0, false);
            signalRenderer.setSeriesPaint(0, Color.red);
            NumberAxis indicatorAxis = new NumberAxis();
            indicatorAxis.setAutoRangeIncludesZero(false);
            XYPlot indicator3Plot = new XYPlot(equityTSC, null, indicatorAxis, signalRenderer);
            combinedDomainPlot.add(indicator3Plot, 1);

            // Create main chart
            this.chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, combinedDomainPlot, true);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setFillZoomRectangle(true);
            chartPanel.setMouseWheelEnabled(true);
            chartPanel.setPreferredSize(new Dimension(1480, 1020));

            // Application frame
            ApplicationFrame frame = new ApplicationFrame("");
            frame.setContentPane(chartPanel);
            frame.pack();
            UIUtils.centerFrameOnScreen(frame);
            frame.setVisible(true);
        });
        // Start the chart thread
        SwingUtilities.invokeLater(chartThread);
    }

}
