package com.ing.httpreqanalyser.service;

import com.ing.httpreqanalyser.model.LogDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ApplicationStart implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(ApplicationStart.class);

    private GenerateReport generateReport;

    @Value("${file.path}")
    private String dirOrFilePath;

    @Autowired
    public ApplicationStart(GenerateReport generateReport) {
        this.generateReport = generateReport;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            Instant start = Instant.now();
            logger.info("The processing of the log files has started.");

            if (args.length > 0) {
                dirOrFilePath = args[0];
                //In case the input file path contains \" in the end
                dirOrFilePath = dirOrFilePath.replace("\"", "");
            } else {
                logger.info("No Directory or file path provided in the arguments " +
                        "so picking the filePath from the application.properties file");
            }

            List<LogDetail> logDetailsList = ReadFile.readFile(dirOrFilePath);

            if (logDetailsList.isEmpty()) {
                logger.error("No Log Files present in the specified input folder.");
            } else {
                String outputFilePath = getOutputFileName(dirOrFilePath);
                generateReport.generateReport(logDetailsList, outputFilePath);

                Instant finish = Instant.now();
                long timeElapsed = Duration.between(start, finish).toMillis();
                logger.info(String.format("The processing of the log files has completed.Total Elapsed time is %s ms", timeElapsed));
            }
        } catch (Exception e) {
            logger.error(String.format("Exception during execution of the application. %s", e.getMessage()));
        }
    }

    private String getOutputFileName(String dirOrFilePath) {
        Path path = new File(dirOrFilePath).toPath();
        // Check if it's a directory
        boolean isDirectory = Files.isDirectory(path);
        DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        if (isDirectory) {
            return dirOrFilePath
                    + "\\"
                    + "output_"
                    + timeStampPattern.format(LocalDateTime.now())
                    + ".csv";
        } else {
            return dirOrFilePath.substring(0, dirOrFilePath.lastIndexOf("\\"))
                    + "\\"
                    + path.getFileName().toString().split("\\.")[0]
                    + "_output_"
                    + timeStampPattern.format(LocalDateTime.now())
                    + ".csv";
        }
    }
}
