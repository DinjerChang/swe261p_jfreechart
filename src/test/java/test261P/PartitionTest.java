package test261P;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;



/**
 * Tests for a scatter plot.
 */
public class PartitionTest {

    /** A chart. */
    private JFreeChart scatterChart;

    /**
     * Common test setup.
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
     * Test Integer value
     */
    @Test
    public void testNumberOfSeries(){
        JFreeChart defaultScatterChart = this.scatterChart;

        Integer NumberOfSeries = defaultScatterChart.getPlot().getLegendItems().getItemCount();
        assertEquals(3,NumberOfSeries);
    }

    /**
     * Our default Scatter Plot has a border ranging from 1.0 ~ 4.0 on X-axis
     *  and from 1.0 ~ 6.0 from Y-axis
     */
    @Test
    public void testScatterPlotXaxisUpperBound(){
        XYPlot<String> defaultScatterChart = (XYPlot<String>) this.scatterChart.getPlot();
        Range rangeX = defaultScatterChart.getDomainAxis().getRange();
        assertTrue(rangeX.getUpperBound() > 4.0);
    }

    @Test
    public void testScatterPlotXaxisLowerBound(){
        XYPlot<String> defaultScatterChart = (XYPlot<String>) this.scatterChart.getPlot();
        Range rangeX = defaultScatterChart.getDomainAxis().getRange();
        assertTrue(rangeX.getUpperBound() <1.0);
    }

    @Test
    public void testScatterPlotYaxisLowerBound(){
        XYPlot<String> defaultScatterChart = (XYPlot<String>) this.scatterChart.getPlot();
        Range rangeX = defaultScatterChart.getRangeAxis().getRange();
        assertTrue(rangeX.getLowerBound() < 1.0);
    }

    @Test
    public void testScatterPlotYaxisUpperBound(){
        XYPlot<String> defaultScatterChart = (XYPlot<String>) this.scatterChart.getPlot();
        Range rangeX = defaultScatterChart.getRangeAxis().getRange();
        assertTrue(rangeX.getUpperBound() > 6.0);
    }


    /**
     * Replaces the dataset and checks that it has changed as expected.
     */
    @Test
    public void testReplaceDataset() {

        // create a dataset...
        XYSeries<String> series1 = new XYSeries<>("Series 1");
        series1.add(10.0, 10.0);
        series1.add(20.0, 20.0);
        series1.add(30.0, 30.0);
        XYDataset<String> dataset = new XYSeriesCollection<>(series1);

        LocalListener l = new LocalListener();
        this.scatterChart.addChangeListener(l);

        @SuppressWarnings("unchecked")
        XYPlot<String> plot = (XYPlot) this.scatterChart.getPlot();
        plot.setDataset(dataset);
        assertTrue(l.flag);
    }

    /**
     * Test DataSet Label: Null
     */
    @Test
    public void testReplaceKeyWithNull(){
        LocalListener l = new LocalListener();
        this.scatterChart.addChangeListener(l);
        XYPlot<String> defaultScatterChart = (XYPlot<String>) this.scatterChart.getPlot();
        boolean isReplace = false;

        try{
            XYSeries<String> series1 = new XYSeries<>(null);
            series1.add(10.0, 10.0);
            series1.add(20.0, 20.0);
            series1.add(30.0, 30.0);
            XYDataset<String> dataset = new XYSeriesCollection<>(series1);
            defaultScatterChart.setDataset(dataset);
        }catch (IllegalArgumentException e){
            isReplace = true;
        }
        assertFalse(l.flag);
        assertTrue(isReplace);
    }

    /**
     * Test DataSet Label: String
     */
    @Test
    public void testReplaceKeyWithEmptyString(){
        LocalListener l = new LocalListener();
        this.scatterChart.addChangeListener(l);
        XYPlot<String> defaultScatterChart = (XYPlot<String>) this.scatterChart.getPlot();
        XYSeries<String> series1 = new XYSeries<>("");
        series1.add(10.0, 10.0);
        series1.add(20.0, 20.0);
        series1.add(30.0, 30.0);
        XYDataset<String> dataset = new XYSeriesCollection<>(series1);
        defaultScatterChart.setDataset(dataset);
        String firstKey = this.scatterChart.getPlot().getLegendItems().get(0).getLabel();
        assertEquals("",firstKey);
    }




    /**
     * Do nothing and checks the plot isn't changed
     */
    @Test
    public void testPlotNeverChange() {
        LocalListener l = new LocalListener();
        this.scatterChart.addChangeListener(l);
        assertFalse(l.flag);
    }


    @Test
    public void testDrawWithNullInfo() {
        try {
            BufferedImage image = new BufferedImage(200, 100,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            this.scatterChart.draw(g2, new Rectangle2D.Double(0, 0, 200, 100), null,
                    null);
            g2.dispose();
        }
        catch (Exception e) {
            fail("No exception should be thrown.");
        }
    }
}