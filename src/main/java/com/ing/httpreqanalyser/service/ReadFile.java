package com.ing.httpreqanalyser.service;

import com.ing.httpreqanalyser.enums.StatusType;
import com.ing.httpreqanalyser.enums.Type;
import com.ing.httpreqanalyser.exceptions.FilePathNotFound;
import com.ing.httpreqanalyser.exceptions.FileProcessingException;
import com.ing.httpreqanalyser.exceptions.InputArgumentException;
import com.ing.httpreqanalyser.model.LogDetail;
import com.ing.httpreqanalyser.util.DateUtility;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class contains methods for reading the log files.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReadFile {

    private static final Logger logger = LoggerFactory.getLogger(ReadFile.class);

    /**
     * Reads log files from the provided directory and extracts
     * the data from the file into the List of LogDetail objects.
     *
     * @param dirOrFilePath the directory or the file path
     * @return the list of LogDetail objects
     */
    @SneakyThrows
    public static List<LogDetail> readFile(String dirOrFilePath) {
        logger.debug("Inside readFile method");
        List<LogDetail> logDetailList = new ArrayList<>();

        List<File> files = getFiles(dirOrFilePath);

        if (files != null && !files.isEmpty()) {
            for (File file : files) {
                logger.info("Reading file.");
                //read file into stream, try-with-resources
                try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {
                    logDetailList.addAll(stream.parallel()
                            //filter the logDetailsList based on the type REQUEST
                            .filter(p -> p.startsWith(Type.REQUEST.toString()))
                            //If multiple tabs (2 or more) are present in
                            // the file replace it with a single tab
                            .map(line -> line.replaceAll("[\\t]{2,}", "\t"))
                            //Split the line based on the tab
                            .map(line -> line.split("\t"))
                            //add the file data into LogDetail object and add collect the objects into a list.
                            .map(lineArr -> new LogDetail(Type.valueOf(lineArr[0]),
                                    Integer.valueOf(lineArr[1]),
                                    lineArr[2],
                                    DateUtility.getDateFromUnixTimeStamp(lineArr[3]),
                                    DateUtility.getDateFromUnixTimeStamp(lineArr[4]),
                                    StatusType.valueOf(lineArr[5]),
                                    DateUtility.getExecutionTime(lineArr[4], lineArr[3])))
                            .collect(Collectors.toList()));
                    logger.info("Added the LogDetail obj in the list");
                } catch (IOException e) {
                    throw FileProcessingException.builder()
                            .message("Error while reading the file " + file.getName()).build();
                }
            }
        }
        return logDetailList;
    }

    /**
     * Gets the files from the provided Directory/file path.
     *
     * @param dirOrFilePath the directory or file path
     * @return the list of files/file from the provided directory/file path.
     */
    @SneakyThrows
    public static List<File> getFiles(String dirOrFilePath) {
        logger.debug("Inside getFiles method");
        List<File> files = new ArrayList<>();
        try {
            Path path = new File(dirOrFilePath).toPath();
            // Check if it's a directory
            boolean isDirectory = Files.isDirectory(path);
            boolean isExists = Files.exists(path);
            //Check if the provided file/directory path is present if not exit.
            if (!isExists) {
                throw FilePathNotFound.builder()
                        .message("The provided file/directory path does not exist ").build();
            }
            if (isDirectory) {
                files = getFilesFromDirectory(dirOrFilePath);
            } else {
                files.add(new File(dirOrFilePath));
            }
        } catch (InvalidPathException e) {
            throw InputArgumentException.builder()
                    .message("Error while processing the provided file/directory path. " + e.getLocalizedMessage()).build();
        }
        return files;
    }

    /**
     * Get the files from the provided input directory
     *
     * @param dirLocation the directory location
     * @return the list of files from the provided directory
     */
    @SneakyThrows
    public static List<File> getFilesFromDirectory(String dirLocation) {
        logger.debug("Getting the files from directory");
        try (Stream<Path> stream = Files.list(Paths.get(dirLocation)).filter(s -> {
            Optional<String> extension = getExtension(s.getFileName().toString());
            return extension.isPresent() && extension.get().equals("log");
        })) {
            return stream.map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(String.format("Exception while getting files from the directory location %s", e.getMessage()));
            throw FileProcessingException.builder().message("Error while reading the files from the provided directory" + dirLocation).build();
        }

    }

    /**
     * Get the extension from the provided file name.
     *
     * @param filename the input file name
     * @return the Optional<String> the extension of the input file
     */
    public static Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

}
