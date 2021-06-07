package com.ing.httpreqanalyser.model;

import com.ing.httpreqanalyser.enums.StatusType;
import com.ing.httpreqanalyser.enums.Type;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * POJO class for holding the details for each row
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class LogDetail {

    private final Type type;
    private final Integer id;
    private final String endpoint;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final StatusType status;
    private final Long executionTime;

}
