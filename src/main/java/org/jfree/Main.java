package org.jfree;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        String chartRoot = "src/main/chart_photos/";
        XYSeriesCollection<String> seriesCollection = new XYSeriesCollection<>();
        XYSeries<String> series1 = new XYSeries<>("GroupA");
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
        JFreeChart scatterChart = ChartFactory.createScatterPlot("Scatter Plot", "Domain",
                "Range", dataset);
        try{
            ChartUtils.saveChartAsPNG(new File("src/main/java/" + "scatter_chart.png"), scatterChart,500, 500);
        }catch(IOException ex){
            System.out.println("Saving chart as PNG should not throw an exception");
        }
    }
}
