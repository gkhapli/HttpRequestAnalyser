package com.ing.httpreqanalyser.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileProcessingExceptionTest {

    @Test
    public void testBuilder() {
        assertNotNull("Object is null", FileProcessingException.builder().build());
    }

    @Test
    public void testGetters() {
        FileProcessingException exception = FileProcessingException.builder()
                .message("Error while reading the files from the provided directory").build();
        assertEquals("Message is different", "Error while reading the files from the provided directory"
                , exception.getMessage());
    }

}
