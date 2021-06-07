package com.ing.httpreqanalyser.enums;

public enum Constants {

    ENDPOINT("ENDPOINT"),
    TOTAL_REQUESTS("TOTAL REQUESTS"),
    TOTAL_FAILED_REQUESTS("TOTAL FAILED REQUESTS"),
    AVERAGE_RESPONSE_TIME("AVERAGE RESPONSE TIME(IN MS)"),
    PERCENTILE95("PERCENTILE 95 (IN MS)"),
    PERCENTILE99("PERCENTILE 99 (IN MS)");

    private final String description;

    Constants(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
