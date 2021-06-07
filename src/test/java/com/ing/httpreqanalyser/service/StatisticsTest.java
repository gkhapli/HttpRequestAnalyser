package com.ing.httpreqanalyser.service;

import com.ing.httpreqanalyser.enums.StatusType;
import com.ing.httpreqanalyser.enums.Type;
import com.ing.httpreqanalyser.model.LogDetail;
import com.ing.httpreqanalyser.model.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public class StatisticsTest {

    private static double[] data;
    private static Stream<LogDetail> testStream;
    private Statistics statistics;
    private Map<String, List<LogDetail>> testMap = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        statistics = new Statistics();

        data = new double[]{3239, 3282, 3547, 3372, 3844,
                3684, 3389, 3512, 3586, 3504, 3772,
                4025, 3573, 3726, 3341, 4066, 3260,
                3612, 3413, 3689, 3581, 3323, 4032,
                3862, 3368, 3904, 3907, 3546, 3424, 3642};

        List<LogDetail> logDetailList = new ArrayList<>();

        logDetailList.add(new LogDetail(Type.REQUEST, 9, "api/test", LocalDateTime.of(2019, 5, 17, 11, 45, 40, 382000000),
                LocalDateTime.of(2019, 5, 17, 11, 45, 49, 421000000), StatusType.OK, 9039l));

        logDetailList.add(new LogDetail(Type.REQUEST, 11, "api/test", LocalDateTime.of(2019, 5, 17, 11, 45, 40, 603000000),
                LocalDateTime.of(2019, 5, 17, 11, 45, 49, 416000000), StatusType.OK, 8813l));

        logDetailList.add(new LogDetail(Type.REQUEST, 4, "api/test", LocalDateTime.of(2019, 5, 17, 11, 45, 39, 961000000),
                LocalDateTime.of(2019, 5, 17, 11, 45, 49, 398000000), StatusType.OK, 9437l));

        logDetailList.add(new LogDetail(Type.REQUEST, 15, "api/test", LocalDateTime.of(2019, 5, 17, 11, 45, 42, 462000000),
                LocalDateTime.of(2019, 5, 17, 11, 45, 49, 403000000), StatusType.OK, 6941l));

        logDetailList.add(new LogDetail(Type.REQUEST, 3, "api/test", LocalDateTime.of(2019, 5, 17, 11, 45, 39, 662000000),
                LocalDateTime.of(2019, 5, 17, 11, 45, 49, 417000000), StatusType.OK, 9755l));

        logDetailList.add(new LogDetail(Type.REQUEST, 5, "api/test", LocalDateTime.of(2019, 5, 17, 11, 45, 40, 3000000),
                LocalDateTime.of(2019, 5, 17, 11, 45, 49, 409000000), StatusType.OK, 9406l));

        logDetailList.add(new LogDetail(Type.REQUEST, 17, "api/test", LocalDateTime.of(2019, 5, 17, 11, 45, 43, 212000000),
                LocalDateTime.of(2019, 5, 17, 11, 45, 49, 412000000), StatusType.KO, 6200l));

        logDetailList.add(new LogDetail(Type.REQUEST, 2, "api/test", LocalDateTime.of(2019, 5, 17, 11, 45, 39, 554000000),
                LocalDateTime.of(2019, 5, 17, 11, 45, 49, 413000000), StatusType.KO, 9859l));

        logDetailList.add(new LogDetail(Type.REQUEST, 12, "api/test", LocalDateTime.of(2019, 5, 17, 11, 45, 41, 733000000),
                LocalDateTime.of(2019, 5, 17, 11, 45, 49, 408000000), StatusType.KO, 7675l));

        testMap.put("/api/test", logDetailList);

        testStream = logDetailList.stream();
    }

    @Test
    public void getOutputResult() {
        Result actualResult = null;
        for (Map.Entry<String, List<LogDetail>> map : testMap.entrySet()) {
            actualResult = statistics.getOutputResult().apply(map);
        }

        Result expected = new Result(9l, 3l, 8898.5, 9755.0, 9755.0);
        Assert.assertEquals(expected, actualResult);
    }

    @Test
    public void getTotalRequests() {
        long totalRequests = statistics.getTotalRequests().apply(testStream);
        Assert.assertEquals(9, totalRequests);
    }

    @Test
    public void getTotalFailedRequests() {
        long totalFailedRequests = statistics.getTotalFailedRequests().apply(testStream);
        Assert.assertEquals(3, totalFailedRequests);
    }

    @Test
    public void getAverageResponseTime() {
        double averageResponseTime = statistics.getAverageResponseTime().apply(testStream);
        Assert.assertEquals(8898.5, averageResponseTime, 0.05);
    }

    @Test
    public void getNthPercentile_95() {
        double actual = statistics.getNthPercentile(Arrays.stream(data), 95);
        Assert.assertEquals(4032.0, actual, 0.0);
    }

    @Test
    public void getNthPercentile_99() {

        double actual = statistics.getNthPercentile(Arrays.stream(data), 99);
        Assert.assertEquals(4066.0, actual, 0.0);

    }
}