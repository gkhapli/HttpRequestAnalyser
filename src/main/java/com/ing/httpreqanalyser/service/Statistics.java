package com.ing.httpreqanalyser.service;

import com.ing.httpreqanalyser.enums.StatusType;
import com.ing.httpreqanalyser.model.LogDetail;
import com.ing.httpreqanalyser.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * Class for calculation of Statistics to be displayed in the output report
 */
@Component
public final class Statistics {

    private final Logger logger = LoggerFactory.getLogger(Statistics.class);

    /**
     * Gets the Result object based on the provided input Map.
     *
     * @return the Result object
     */
    public Function<Map.Entry<String, List<LogDetail>>, Result> getOutputResult() {
        logger.debug("Getting the output result object");
        return map -> {

            Double avgResponseTime = getAverageResponseTime().apply(map.getValue().stream());
            Long totalRequests = getTotalRequests().apply(map.getValue().stream());
            Long totalFailedRequests = getTotalFailedRequests().apply(map.getValue().stream());
            Double percentile95th = getNthPercentile(map.getValue().stream()
                    .filter(p -> p.getStatus().toString().startsWith(StatusType.OK.toString()))
                    .mapToDouble(LogDetail::getExecutionTime), 95.0);
            Double percentile99th = getNthPercentile(map.getValue().stream()
                    .filter(p -> p.getStatus().toString().startsWith(StatusType.OK.toString()))
                    .mapToDouble(LogDetail::getExecutionTime), 99.0);

            logger.debug("Getting the output result object completed");
            return new Result(totalRequests, totalFailedRequests,
                    avgResponseTime, percentile95th, percentile99th);
        };
    }

    /**
     * Gets the Total Requests count for a specific Endpoint
     *
     * @return the total requests
     */
    public Function<Stream<LogDetail>, Long> getTotalRequests() {
        return Stream::count;
    }

    /**
     * Gets the Total Failed Requests count for a specific Endpoint
     *
     * @return the total failed requests
     */
    public Function<Stream<LogDetail>, Long> getTotalFailedRequests() {
        logger.debug("Getting the TotalFailedRequests");
        return e -> e.filter(a -> a.getStatus().toString().startsWith(StatusType.KO.toString()))
                .count();
    }

    /**
     * gets the Average Response Time for the specific endpoint
     *
     * @return the average response time
     */
    public Function<Stream<LogDetail>, Double> getAverageResponseTime() {
        logger.debug("Getting the averageResponseTime");
        return e -> {
            OptionalDouble avgOptional = e
                    .filter(p -> p.getStatus().toString().startsWith(StatusType.OK.toString()))
                    .mapToLong(LogDetail::getExecutionTime)
                    .average();
            return avgOptional.isPresent() ? avgOptional.getAsDouble() : 0;
        };
    }

    /**
     * Calculates the Nth percentile for a specific endpoint based on the execution time.
     * Here we are using the Nearest Rank Algorithm for calculation of the Percentile Values.
     *
     * @param valueStream the Double stream
     * @param percentile  the value of N
     * @return the percentile for the specific endpoint
     */
    public Double getNthPercentile(DoubleStream valueStream, double percentile) {
        logger.debug("Getting the NthPercentile");
        List<Double> doubleList = valueStream.sorted().boxed().collect(Collectors.toList());
        int arrayIndex = (int) Math.round(doubleList.size() * (percentile / 100) + 0.5) - 1;
        return doubleList.get(arrayIndex);
    }

}
