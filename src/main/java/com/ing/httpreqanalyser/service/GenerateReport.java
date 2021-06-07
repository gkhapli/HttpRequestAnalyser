package com.ing.httpreqanalyser.service;

import com.ing.httpreqanalyser.exceptions.OutputFileGenerationException;
import com.ing.httpreqanalyser.model.LogDetail;
import com.ing.httpreqanalyser.model.Result;
import com.ing.httpreqanalyser.util.GenerateCSV;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is used for Generation of the output in csv format.
 */
@Component
public final class GenerateReport {

    private final Logger logger = LoggerFactory.getLogger(GenerateReport.class);

    private Statistics statistics;

    @Autowired
    public GenerateReport(Statistics statistics) {
        this.statistics = statistics;
    }

    /**
     * Accepts the log details list and generates the report
     *
     * @param logDetailsList the log details list
     */
    @SneakyThrows
    public boolean generateReport(List<LogDetail> logDetailsList, String outputFilePath) {
        logger.info("Generating Report");
        Map<String, List<LogDetail>> groupByEndpoint = logDetailsList.stream()
                .collect(Collectors
                        .groupingBy(LogDetail::getEndpoint));

        Map<String, Result> outputResultMap = groupByEndpoint.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        statistics.getOutputResult()));
        try {
            GenerateCSV.generateCSV(outputResultMap, outputFilePath);
        } catch (IOException e) {
            throw OutputFileGenerationException.builder().message("Error while storing the csv file in the output folder."
                    + e.getMessage()).build();
        }
        logger.info("Report generated successfully and saved at " + outputFilePath);
        return true;

    }

}
