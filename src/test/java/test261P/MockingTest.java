package test261P;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.swing.ChartFrame;
import org.jfree.chart.swing.ChartPanel;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import  static org.mockito.Mockito.*;

public class MockingTest {


    @Test
    public void testMockedXYDatasetInteractionWithJFreeChart(){
        XYDataset<String> xyDataset = mock(XYDataset.class);
        String[] keys = new String[]{"Group A", "Group B","Group C"};
        when(xyDataset.getSeriesCount()).thenReturn(keys.length);
        // set the expected return of getSeriesKey
        for (int i =0; i < keys.length - 1;i++) {
            when(xyDataset.getSeriesKey(i)).thenReturn(keys[i]);
        }
        xyDataset.getSeriesKey(0); // return GroupA
        xyDataset.getSeriesKey(0); // return GroupA
        xyDataset.getSeriesKey(1); // return GroupB
        assertEquals(3,xyDataset.getSeriesCount());

        //test the independence of getSeriesKey method
        verify(xyDataset,times(1)).getSeriesCount();
        verify(xyDataset,times(2)).getSeriesKey(0);
        verify(xyDataset,times(1)).getSeriesKey(1);
        verify(xyDataset,never()).getSeriesKey(2);

        // test the interaction between ChartFactory object and XYDataSet object

        /*
         when creating JFreeChart by ChartFactory.createScatterPlot, getSeriesKey will be called
         by the Object org.jfree.chart.renderer.xy.AbstractXYItemRenderer which is called by createScatterPlot
         two times
         */

        // create scatter chart with mocked XYDataset
        JFreeChart scatterChart = ChartFactory.createScatterPlot("Scatter Chart","Domain", "Range",xyDataset);
        verify(xyDataset,times(2)).getSeriesKey(2);

        // since we already called getSeries twice at line 34 and 35, this will add the interaction time to 4
        verify(xyDataset,times(4)).getSeriesKey(0);

        verify(xyDataset,times(3)).getSeriesKey(1);
    }
}
