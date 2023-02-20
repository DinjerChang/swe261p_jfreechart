package test261P;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.axis.NumberAxis;


import org.jfree.chart.plot.XYPlot;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FiniteStateMachineTest {


    /**
     * Test DataSet State
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
     * Test Plot State
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
    public void testJFreeChartState()  {
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
     * Test Transition ScatterDataSet -> ScatterPlot
     * check if the dataset value are same between dataSet and plot
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

        XYPlot xyPlot = new XYPlot<>();
        xyPlot.setDataset(scatterDataset);
        assertEquals(xyPlot.getDataset(), scatterDataset);
    }

    /**
     * Test Transition ScatterPlot -> ScatterChart
     * check if the plot are the same
     */
    @Test
    public void testTransitionPlotToChart()  {
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
        XYPlot xyPlot = new XYPlot<>();
        xyPlot.setDataset(scatterDataset);

        JFreeChart chart = new JFreeChart("Chart Title", JFreeChart.DEFAULT_TITLE_FONT, xyPlot, true);
        assertNotNull(chart);
        assertEquals(chart.getPlot(),xyPlot);
    }

    /**
     * Test Transition ScatterData -> ScatterChart
     * check if the dataset value are same between data and chart
     */
    @Test
    public void testTransitionDataToChart() {
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
        XYPlot xyPlot = new XYPlot<>();
        xyPlot.setDataset(scatterDataset);

        JFreeChart scatterPlot = ChartFactory.createScatterPlot("Scatter Chart","Domain", "Range",scatterDataset);
        assertEquals(((XYPlot) scatterPlot.getPlot()).getDataset(), xyPlot.getDataset());
    }

    /**
     * Test Transition Plot -> Data
     * modify data from plot object
     */
    @Test
    public void testTransitionPlotToData() {
        XYSeriesCollection<String> oldDataset = new XYSeriesCollection<>();
        XYSeries<String> series1 = new XYSeries<>("Group A");
        series1.add(1.0, 6.0);
        series1.add(1.5, 1.0);

        XYSeries<String> series2 = new XYSeries<>("Group B");
        series2.add(2.0,2.5);
        series2.add(2.5,2.0);

        oldDataset.addSeries(series1);
        oldDataset.addSeries(series2);
        XYPlot xyPlot = new XYPlot<>();
        xyPlot.setDataset(oldDataset);

        XYSeriesCollection<String> newDataset = new XYSeriesCollection<>();
        XYSeries<String> series3 = new XYSeries<>("Group A");
        series1.add(10.0, 60.0);
        series1.add(10.5, 10.0);

        XYSeries<String> series4 = new XYSeries<>("Group B");
        series2.add(20.0,20.5);
        series2.add(20.5,20.0);

        newDataset.addSeries(series3);
        newDataset.addSeries(series4);

        assertEquals(oldDataset, xyPlot.getDataset());
        // plot set new value
        xyPlot.setDataset(newDataset);
        assertEquals(newDataset, xyPlot.getDataset());
    }

    /**
     * Test Transition Chart <-> Plot
     * modify data from chart object
     */
    @Test
    public void testTransitionChartToPlot() {
        XYSeriesCollection<String> oldDataset = new XYSeriesCollection<>();
        XYSeries<String> series1 = new XYSeries<>("Group A");
        series1.add(1.0, 6.0);
        series1.add(1.5, 1.0);

        XYSeries<String> series2 = new XYSeries<>("Group B");
        series2.add(2.0,2.5);
        series2.add(2.5,2.0);

        oldDataset.addSeries(series1);
        oldDataset.addSeries(series2);
        JFreeChart scatterPlot = ChartFactory.createScatterPlot("Scatter Chart","Domain", "Range",oldDataset);

        XYSeriesCollection<String> newDataset = new XYSeriesCollection<>();
        XYSeries<String> series3 = new XYSeries<>("Group A");
        series1.add(10.0, 60.0);
        series1.add(10.5, 10.0);

        XYSeries<String> series4 = new XYSeries<>("Group B");
        series2.add(20.0,20.5);
        series2.add(20.5,20.0);

        newDataset.addSeries(series3);
        newDataset.addSeries(series4);

        assertEquals(oldDataset, ((XYPlot)scatterPlot.getPlot()).getDataset());
        // plot set new value
        ((XYPlot)scatterPlot.getPlot()).setDataset(newDataset);
        assertEquals(newDataset, ((XYPlot)scatterPlot.getPlot()).getDataset());
    }

    /**
     * Test Transition Chart -> Chart
     * update the data of chart
     */
    @Test
    public void testTransitionUpdateJFreeChart() {
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

        assertNotEquals(scatterPlot.getTitle().getText(),"scatter plot updated");
        scatterPlot.setTitle("scatter plot updated");
        assertEquals(scatterPlot.getTitle().getText(),"scatter plot updated");
    }

    /**
     * Test Transition  Chart -> PNG file saved on disk
     */
    @Test
    public void testTransitionChartToPNGfile() {
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
        try {
            ChartUtils.saveChartAsPNG(new File("src/test/java/test261P/test_chart_output.png"), scatterPlot,500, 500);
        }catch(IOException e) {
            fail("Saving chart as PNG file should not throw an exception");
        }
    }
}

