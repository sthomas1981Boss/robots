package com.robots.chart;


import com.robots.models.timeseries.Timeserie;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LineChart extends Chart {

    public void show(Timeserie ts) throws InterruptedException {
        Thread chartThread = new Thread(() -> {
            TimeSeriesCollection tsc = convert(ts);

            // Candlestick rendering
            ValueAxis timeAxis = new DateAxis("Time");
            NumberAxis valueAxis = new NumberAxis("");
            XYLineAndShapeRenderer returnRenderer = new XYLineAndShapeRenderer();
            returnRenderer.setSeriesShapesVisible(0, false);
            returnRenderer.setSeriesPaint(0, Color.red);

            CombinedDomainXYPlot combinedDomainPlot = new CombinedDomainXYPlot(timeAxis);
            valueAxis.setAutoRangeIncludesZero(false);
            XYPlot plot = new XYPlot(tsc, (ValueAxis) null, valueAxis, returnRenderer);
            combinedDomainPlot.add(plot, 1);

            // Create main chart
            this.chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, combinedDomainPlot, true);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setFillZoomRectangle(true);
            chartPanel.setMouseWheelEnabled(true);
            chartPanel.setPreferredSize(new Dimension(1000, 600));

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


    public void show(List<Timeserie> tss) throws InterruptedException {

        Thread chartThread = new Thread(() -> {
            // Candlestick rendering
            ValueAxis timeAxis = new DateAxis("Time");
            NumberAxis valueAxis = new NumberAxis("");
            valueAxis.setAutoRangeIncludesZero(false);

            CombinedDomainXYPlot combinedDomainPlot = new CombinedDomainXYPlot(timeAxis);

            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesShapesVisible(0, false);
            renderer.setSeriesShapesVisible(1, false);
            renderer.setSeriesShapesVisible(2, false);
            renderer.setSeriesPaint(0, Color.red);
            renderer.setSeriesPaint(1, Color.blue);
            renderer.setSeriesPaint(2, Color.black);

            TimeSeriesCollection tsc = convert(tss);
            XYPlot plot = new XYPlot(tsc, (ValueAxis) null, valueAxis, renderer);
            combinedDomainPlot.add(plot, 1);

            // Create main chart
            this.chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, combinedDomainPlot, true);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setFillZoomRectangle(true);
            chartPanel.setMouseWheelEnabled(true);
            chartPanel.setPreferredSize(new Dimension(1000, 600));

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
