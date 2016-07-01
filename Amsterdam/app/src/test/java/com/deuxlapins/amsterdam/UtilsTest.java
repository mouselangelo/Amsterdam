package com.deuxlapins.amsterdam;

import com.deuxlapins.amsterdam.util.Utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the Utils static functions
 * Created by chetan on 29/06/16.
 */
public class UtilsTest {

    @Test
    public void testConvertFtoC() throws Exception {
        double valueInF = 212d;
        double expectedValueInC = 100d;
        Assert.assertEquals(expectedValueInC, Utils.convertFtoC(valueInF), 0.0001);
    }

    @Test
    public void testFormatTemperature() throws Exception {
        double value = 35.6d;
        String expectedValue = "36°";
        assertEquals(expectedValue, Utils.formatTemperature(value));
    }
}