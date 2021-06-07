package com.ing.httpreqanalyser.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class DateUtilityTest {

    @Test
    public void getDateFromUnixTimeStamp() {
        LocalDateTime actual = DateUtility.getDateFromUnixTimeStamp("1622844000404");
        LocalDateTime expected = LocalDateTime.of(2021, 6, 5, 00, 0, 0, 404000000);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getExecutionTime() {
        long actual = DateUtility.getExecutionTime("1622844500000", "1622844000000");
        long expected = 500000;
        Assert.assertEquals(expected, actual);
    }
}