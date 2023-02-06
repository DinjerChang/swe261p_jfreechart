package test261P;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
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
        series1.add(1.0, 1.0);
        series1.add(1.2, 1.2);
        series1.add(1.2, 1.0);
        series1.add(1.0, 1.2);

        XYSeries<String> series2 = new XYSeries<>("Group B");
        series2.add(2.0,2.0);
        series2.add(2.5,2.5);
        series2.add(2.0,2.5);
        series2.add(2.5,2.0);

        XYSeries<String> series3 = new XYSeries<>("Group C");
        series3.add(4.0,3.0);
        series3.add(2.6,2.6);
        series3.add(2.6,3.0);
        series3.add(3.0,2.6);

        seriesCollection.addSeries(series1);
        seriesCollection.addSeries(series2);
        seriesCollection.addSeries(series3);
        XYDataset<String> dataset = seriesCollection;

        return ChartFactory.createScatterPlot("Scatter Plot", "X",
                "Y", dataset);
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
        System.out.println(defaultScatterChart.getPlot().getLegendItems().get(0).getSeriesKey());
        Integer NumberOfSeries = defaultScatterChart.getPlot().getLegendItems().getItemCount();
        assertEquals(3,NumberOfSeries);
    }


    @Test
    public void testBoundary(){
        XYPlot<String> defaultScatterChart = (XYPlot<String>) this.scatterChart.getPlot();
        Range rangeX = defaultScatterChart.getDomainAxis().getRange();
        assertTrue(rangeX.getLowerBound() < 1.0);
        assertTrue(rangeX.getUpperBound() > 4.0);
    }


    /**
     * Draws the chart with a null info object to make sure that no exceptions
     * are thrown (a problem that was occurring at one point).
     */
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
        ValueAxis axis = plot.getRangeAxis();
        Range range = axis.getRange();
        assertTrue(range.getLowerBound() <= 10,
                "Expecting the lower bound of the range to be around 10: " + range.getLowerBound());
        assertTrue(range.getUpperBound() >= 30,
                "Expecting the upper bound of the range to be around 30: " + range.getUpperBound());
    }

    /**
     * Check that setting a tool tip generator for a series does override the
     * default generator.
     */
    @Test
    public void testSetSeriesToolTipGenerator() {
        XYPlot<?> plot = (XYPlot) this.scatterChart.getPlot();
        XYItemRenderer renderer = plot.getRenderer();
        StandardXYToolTipGenerator tt = new StandardXYToolTipGenerator();
        renderer.setSeriesToolTipGenerator(0, tt);
        XYToolTipGenerator tt2 = renderer.getToolTipGenerator(0, 0);
        assertSame(tt2, tt);
    }

}
