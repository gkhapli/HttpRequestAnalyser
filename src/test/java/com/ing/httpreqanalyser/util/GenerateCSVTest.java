package com.ing.httpreqanalyser.util;

import com.ing.httpreqanalyser.model.Result;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenerateCSVTest {

    private Map<String, Result> testMap = new HashMap<>();

    @Before
    public void setUp() {
        Result result = new Result(9l, 3l, 8898.5, 9755.0, 9755.0);
        testMap.put("/api/test", result);
    }

    @Test
    public void generateCSV() throws IOException {
        GenerateCSV.generateCSV(testMap, "src\\test\\resources\\output_test.csv");
    }
}