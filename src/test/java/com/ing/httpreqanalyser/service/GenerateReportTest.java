package com.ing.httpreqanalyser.service;

import com.ing.httpreqanalyser.enums.StatusType;
import com.ing.httpreqanalyser.enums.Type;
import com.ing.httpreqanalyser.exceptions.OutputFileGenerationException;
import com.ing.httpreqanalyser.model.LogDetail;
import com.ing.httpreqanalyser.model.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateReportTest {

    List<LogDetail> logDetailList = new ArrayList<>();
    private Map<String, Result> testMap = new HashMap<>();

    @Before
    public void setUp() {

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

    }

    @Test
    public void generateReport() throws IOException, OutputFileGenerationException {
        Assert.assertTrue(new GenerateReport(new Statistics())
                .generateReport(logDetailList, "src\\test\\resources\\output_test.csv"));
    }
}