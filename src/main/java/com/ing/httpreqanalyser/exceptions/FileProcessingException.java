package com.ing.httpreqanalyser.exceptions;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FileProcessingException extends Exception {

    private String message;
}
