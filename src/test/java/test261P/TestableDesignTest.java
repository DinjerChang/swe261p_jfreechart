package test261P;

import org.jfree.data.io.CSV;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestableDesignTest {

    /*
    default fielddelimiter is comma ,
    default textdelimiter is double quote "
     */
    CSV csvObjDefault = new CSV();

    char comma = "x".charAt(0);
    char semiColon = ";".charAt(0);

    CSV csvObjCustomized = new CSV(comma, semiColon);

    /*
    removeStringDelimitersTestable only use textdelimiter
     */
    @Test
    public void testRemoveStringDelimitersAtBeginWithDefaultCSV() {
        String string = " \"AAA";
        String res = csvObjDefault.removeStringDelimitersTestable(string);
        assertEquals("AAA", res);
    }

    @Test
    public void testRemoveStringDelimitersAtEndWithDefaultCSV(){
        String string = "BBB\"";
        String res = csvObjDefault.removeStringDelimitersTestable(string);
        assertEquals("BBB", res);
    }
    @Test
    public void testRemoveStringDelimitersAtBothWithDefaultCSV(){
        String string = "\"CCC\"";
        String res = csvObjDefault.removeStringDelimitersTestable(string);
        assertEquals("CCC", res);
    }

    @Test
    public void testRemoveStringDelimitersAtBeginWithCustomizedCSV(){
        String string = "\"AAA";
        String res = csvObjCustomized.removeStringDelimitersTestable(string);
        assertNotEquals("AAA", res);

        string = ";AAA";
        res = csvObjCustomized.removeStringDelimitersTestable(string);
        assertEquals("AAA", res);
    }

    @Test
    public void testRemoveStringDelimitersAtEndWithCustomizedCSV(){
        String string = "BBB\"";
        String res = csvObjCustomized.removeStringDelimitersTestable(string);
        assertNotEquals("BBB", res);

        string = "BBB;";
        res = csvObjCustomized.removeStringDelimitersTestable(string);
        assertEquals("BBB", res);
    }


    @Test
    public void testRemoveStringDelimitersAtBothWithCustomizedCSV(){
        String string = "\"CCC\"";
        String res = csvObjCustomized.removeStringDelimitersTestable(string);
        assertNotEquals("CCC", res);

        string = ";CCC;";
        res = csvObjCustomized.removeStringDelimitersTestable(string);
        assertEquals("CCC", res);
    }
}
