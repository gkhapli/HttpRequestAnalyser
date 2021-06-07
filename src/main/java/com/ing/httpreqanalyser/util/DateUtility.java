package com.ing.httpreqanalyser.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

/**
 * Class created for holding the utility methods for interacting with date.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtility {

    /**
     * Get the date from the UnixTimeStamp
     *
     * @param unixTimeStamp the date in UnixTimeStamp format (milliseconds)
     * @return Returns date in LocalDateTime format
     */
    public static LocalDateTime getDateFromUnixTimeStamp(String unixTimeStamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(unixTimeStamp)),
                TimeZone.getDefault().toZoneId());
    }

    /**
     * Finds the execution time
     *
     * @param endTime   the end time in Unix TimeStamp
     * @param startTime the start time in Unix TimeStamp
     * @return the execution time difference between end time and start time in milliseconds
     */
    public static long getExecutionTime(String endTime, String startTime) {
        return getDateFromUnixTimeStamp(startTime)
                .until(getDateFromUnixTimeStamp(endTime), ChronoUnit.MILLIS);
    }
}
