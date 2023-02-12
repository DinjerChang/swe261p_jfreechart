package test261P;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ScatterPlotTest;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.swing.ChartPanel;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FiniteStateMachineTest {
    /** A chart. */
    private JFreeChart scatterChart;

    /**
     * Common test setup.
     * initiate a scatter plot
     */
    @BeforeEach
    public void setUp() {
        this.scatterChart = createChart();
    }

    /**
     * Create a Scatter chart with sample data
     *
     * @return The chart.
     */
    private static JFreeChart createChart() {

        XYSeriesCollection<String> seriesCollection = new XYSeriesCollection<>();
        XYSeries<String> series1 = new XYSeries<>("Group A");
        series1.add(1.0, 6.0);
        series1.add(1.0, 1.5);
        series1.add(1.5, 1.5);
        series1.add(1.5, 1.0);

        XYSeries<String> series2 = new XYSeries<>("Group B");
        series2.add(2.0,2.0);
        series2.add(2.5,2.5);
        series2.add(2.0,2.5);
        series2.add(2.5,2.0);

        XYSeries<String> series3 = new XYSeries<>("Group C");
        series3.add(4.0,3.0);
        series3.add(4.0,3.5);
        series3.add(3.5,3.0);
        series3.add(3.5,4.0);

        seriesCollection.addSeries(series1);
        seriesCollection.addSeries(series2);
        seriesCollection.addSeries(series3);
        XYDataset<String> dataset = seriesCollection;

        return ChartFactory.createScatterPlot("Scatter Plot", "Domain",
                "Range", dataset);
    }

    /**
     * A chart change listener.
     *
     */
    static class LocalListener implements ChartChangeListener {

        /** A flag. */
        private boolean flag = false;

        /**
         * Event handler.
         *
         * @param event  the event.
         */
        @Override
        public void chartChanged(ChartChangeEvent event) {
            this.flag = true;
        }
    }

    /**
     * Test XYDataSet State
     */
    @Test
    public void testScatterDataSetState(){
        XYSeriesCollection<String> seriesCollection = new XYSeriesCollection<>();
        XYSeries<String> series1 = new XYSeries<>("Group A");
        series1.add(1.0, 6.0);
        series1.add(1.5, 1.0);

        XYSeries<String> series2 = new XYSeries<>("Group B");
        series2.add(2.0,2.5);
        series2.add(2.5,2.0);

        seriesCollection.addSeries(series1);
        seriesCollection.addSeries(series2);
        XYDataset<String> scatterDataset = seriesCollection;

        assertEquals("Group A",scatterDataset.getSeriesKey(0));
        assertEquals("Group B",scatterDataset.getSeriesKey(1));
        assertEquals(1,scatterDataset.getXValue(0,0));
        assertEquals(6,scatterDataset.getYValue(0,0));
    }

    /**
     * Test XYPlot State
     */
    @Test
    public void testScatterPlotState(){
        XYSeriesCollection<String> seriesCollection = new XYSeriesCollection<>();
        XYSeries<String> series1 = new XYSeries<>("Group A");
        series1.add(1.0, 6.0);
        series1.add(1.5, 1.0);

        XYSeries<String> series2 = new XYSeries<>("Group B");
        series2.add(2.0,2.5);
        series2.add(2.5,2.0);

        seriesCollection.addSeries(series1);
        seriesCollection.addSeries(series2);
        XYDataset<String> scatterDataset = seriesCollection;

        // XY Plot State
        XYPlot xyPlot = new XYPlot<>();
        xyPlot.setDataset(scatterDataset);
        // x-axis
        xyPlot.setDomainAxis(new NumberAxis("X Axis"));
        // y-axis
        xyPlot.setRangeAxis(new NumberAxis("Y Axis"));

        assertEquals(scatterDataset,xyPlot.getDataset());
        assertEquals("X Axis",xyPlot.getDomainAxis().getLabel());
        assertEquals("Y Axis",xyPlot.getRangeAxis().getLabel());
    }

    /**
     * Test JFree Chart State
     */
    @Test
    public void testJFreeChartState(){
        XYSeriesCollection<String> seriesCollection = new XYSeriesCollection<>();
        XYSeries<String> series1 = new XYSeries<>("Group A");
        series1.add(1.0, 6.0);
        series1.add(1.5, 1.0);

        XYSeries<String> series2 = new XYSeries<>("Group B");
        series2.add(2.0,2.5);
        series2.add(2.5,2.0);

        seriesCollection.addSeries(series1);
        seriesCollection.addSeries(series2);
        XYDataset<String> scatterDataset = seriesCollection;

        JFreeChart scatterPlot = ChartFactory.createScatterPlot("Scatter Chart","Domain", "Range",scatterDataset);
        assertEquals("Scatter Chart", scatterPlot.getTitle().getText());
        assertEquals(scatterDataset, ((XYPlot)scatterPlot.getPlot()).getDataset());
    }

    /**
     * Test ChartPanel State
     */
//    @Test
//    public void testChartPanelState(){
//        XYSeriesCollection<String> seriesCollection = new XYSeriesCollection<>();
//        XYSeries<String> series1 = new XYSeries<>("Group A");
//        series1.add(1.0, 6.0);
//        series1.add(1.5, 1.0);
//
//        XYSeries<String> series2 = new XYSeries<>("Group B");
//        series2.add(2.0,2.5);
//        series2.add(2.5,2.0);
//
//        seriesCollection.addSeries(series1);
//        seriesCollection.addSeries(series2);
//        XYDataset<String> scatterDataset = seriesCollection;
//
//        JFreeChart scatterPlot = ChartFactory.createScatterPlot("Scatter Chart","Domain", "Range",scatterDataset);
//        ChartPanel chartPanel = new ChartPanel(scatterPlot);
//
//
//        assertEquals(scatterPlot, chartPanel.getChart());
//
//    }
    /**
     * Test Transition ScatterData -> ScatterPlot
     */
    @Test
    public void testTransitionDataToPlot() {
        XYSeriesCollection<String> seriesCollection = new XYSeriesCollection<>();
        XYSeries<String> series1 = new XYSeries<>("Group A");
        series1.add(1.0, 6.0);
        series1.add(1.5, 1.0);

        XYSeries<String> series2 = new XYSeries<>("Group B");
        series2.add(2.0,2.5);
        series2.add(2.5,2.0);

        seriesCollection.addSeries(series1);
        seriesCollection.addSeries(series2);
        XYDataset<String> scatterDataset = seriesCollection;

        JFreeChart scatterPlot = ChartFactory.createScatterPlot("Scatter Chart","Domain", "Range",scatterDataset);

//        assertNotNull(pieChart.getPlot());
//        assertEquals(((PiePlot) pieChart.getPlot()).getDataset(), data);
    }
}

