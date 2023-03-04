package test261P;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.data.xy.XYDataset;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import  static org.mockito.Mockito.*;

public class MockingTest {
    @Test
    public void testMockedXYDatasetInteractionWithJFreeChart(){

        XYDataset<String> xyDataset = mock(XYDataset.class);
        String[] keys = new String[]{"Group A", "Group B","Group C"};

        // setUp mockito customized return
        when(xyDataset.getSeriesCount()).thenReturn(keys.length);
        // set the expected return of getSeriesKey
        for (int i =0; i < keys.length ;i++){
            when(xyDataset.getSeriesKey(i)).thenReturn(keys[i]);
        }

        xyDataset.getSeriesKey(0); // return GroupA
        xyDataset.getSeriesKey(0); // return GroupA
        xyDataset.getSeriesKey(1); // return GroupB
        assertEquals(3,xyDataset.getSeriesCount());
        verify(xyDataset,times(1)).getSeriesCount();

        //test the independence of getSeriesKey method
        verify(xyDataset,times(2)).getSeriesKey(0);
        verify(xyDataset,times(1)).getSeriesKey(1);
        verify(xyDataset,never()).getSeriesKey(2);

        // test the interaction between ChartFactory object and XYDataSet object
        // create scatter chart with mocked XYDataset

        JFreeChart scatterChart = ChartFactory.createScatterPlot("Scatter Chart","Domain","Range",xyDataset);
        /*
         when creating JFreeChart by ChartFactory.createScatterPlot, getSeriesKey will be called
         by the function findDomainBounds and findRangeBounds of the
         Object org.jfree.chart.renderer.xy.AbstractXYItemRenderer, which is called by createScatterPlot
         two times
         */
        verify(xyDataset,times(2)).getSeriesKey(2);

        // since we already called getSeries twice at line 34 and 35, this will add the interaction time to 4
        verify(xyDataset,times(4)).getSeriesKey(0);
        verify(xyDataset,times(3)).getSeriesKey(1);
    }

    @Test
    public void testMockedPlotInteractionWithJFreeChart(){
        Plot mockedPlot = mock(Plot.class);

        /*
        when creating the JFreeChart with Plot, it will call the function
        some public function of Plot, such as setChart and AddChangeListener
         */
        JFreeChart chart = new JFreeChart("Title", mockedPlot);

        when(mockedPlot.getBackgroundPaint()).thenReturn(Color.WHITE);
        assertEquals(Color.WHITE, mockedPlot.getBackgroundPaint());

        verify(mockedPlot,times(1)).getBackgroundPaint();

        // the interaction between plot and JFreeChart
        verify(mockedPlot,times(1)).addChangeListener(any());
        verify(mockedPlot, times(1)).setChart(chart);

        // verify there's no more interaction related to the mock after wanted verification
        verifyNoMoreInteractions(mockedPlot);
    }

}
