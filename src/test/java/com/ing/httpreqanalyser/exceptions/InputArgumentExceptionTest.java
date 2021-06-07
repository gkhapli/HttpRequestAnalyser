package com.ing.httpreqanalyser.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InputArgumentExceptionTest {

    @Test
    public void testBuilder() {
        assertNotNull("Object is null", InputArgumentException.builder().build());
    }

    @Test
    public void testGetters() {
        InputArgumentException exception = InputArgumentException.builder()
                .message("The provided file/directory path does not exist.").build();
        assertEquals("Message is different", "The provided file/directory path does not exist."
                , exception.getMessage());
    }

}