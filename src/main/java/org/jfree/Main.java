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

        XYSeries<String> series1 = new XYSeries<>("Series 1");
        series1.add(1.0, 1.0);
        series1.add(2.0, 2.0);
        series1.add(3.0, 3.0);
        XYDataset<String> dataset = new XYSeriesCollection<>(series1);
        JFreeChart scatterChart = ChartFactory.createScatterPlot("Scatter Plot", "Domain",
                "Range", dataset);
        try{
            ChartUtils.saveChartAsPNG(new File(chartRoot + "scatter_chart.png"), scatterChart,500, 500);
        }catch(IOException ex){
            System.out.println("Saving chart as PNG should not throw an exception");
        }
    }
}
