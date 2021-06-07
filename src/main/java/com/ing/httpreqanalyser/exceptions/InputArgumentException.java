package com.ing.httpreqanalyser.exceptions;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InputArgumentException extends Exception {

    private String message;
}
