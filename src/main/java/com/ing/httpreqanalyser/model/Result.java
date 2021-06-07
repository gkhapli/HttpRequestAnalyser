package com.ing.httpreqanalyser.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * The Result Object
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Result {

    /**
     * Variable to store Total number of Requests
     */
    private final Long totalRequests;
    /**
     * Variable to store Total number of Failed Requests
     */
    private final Long totalFailedRequests;

    /**
     * Variable to store Average Response Time
     */
    private final double avgResponseTime;

    /**
     * Variable to store 95 percentile
     */
    private final double percentile95;

    /**
     * Variable to store 99 percentile
     */
    private final double percentile99;

}
