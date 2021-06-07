package com.ing.httpreqanalyser.service;

import org.junit.Test;

public class ApplicationStartTest {

    @Test
    public void run() throws Exception {
        String[] args = {"src\\test\\resources\\test_1.log"};

        new ApplicationStart(
                new GenerateReport(
                        new Statistics()))
                .run(args);


    }
}