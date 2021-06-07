package com.ing.httpreqanalyser.util;

import com.ing.httpreqanalyser.enums.Constants;
import com.ing.httpreqanalyser.model.Result;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class contains the methods for generating the Output CSV file.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GenerateCSV {

    /**
     * This method converts the String array to a single string separated by comma.
     *
     * @param data the String Array for each row
     * @return the string containing a row of csv file
     */
    private static String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }

    /**
     * Generates the output CSV containing the Report.
     *
     * @param outputResultMap the outputResultMap containing the output data
     * @throws IOException the exception thrown in case of issues related to file storage.
     */
    public static boolean generateCSV(Map<String, Result> outputResultMap, String outputFilePath) throws IOException {
        List<String[]> rowData = new ArrayList<>();
        rowData.add(new String[]{
                Constants.ENDPOINT.getDescription(), Constants.TOTAL_REQUESTS.getDescription(),
                Constants.TOTAL_FAILED_REQUESTS.getDescription(), Constants.AVERAGE_RESPONSE_TIME.getDescription(),
                Constants.PERCENTILE95.getDescription(), Constants.PERCENTILE99.getDescription()
        });

        outputResultMap.forEach((endpoint, outputResult) -> {
            rowData.add(new String[]{endpoint, outputResult.getTotalRequests().toString(),
                    outputResult.getTotalFailedRequests().toString(),
                    String.valueOf(Math.round(outputResult.getAvgResponseTime())),
                    String.valueOf(Math.round(outputResult.getPercentile95())),
                    String.valueOf(Math.round(outputResult.getPercentile99()))});
        });

        File csvOutputFile = new File(outputFilePath);

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            rowData.stream()
                    .map(GenerateCSV::convertToCSV)
                    .forEach(pw::println);
        }
        return true;
    }

}
