package com.ing.httpreqanalyser.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OutputFileGenerationExceptionTest {

    @Test
    public void testBuilder() {
        assertNotNull("Object is null", OutputFileGenerationException.builder().build());
    }

    @Test
    public void testGetters() {
        OutputFileGenerationException exception = OutputFileGenerationException.builder()
                .message("Error while storing the csv file in the output folder.").build();
        assertEquals("Message is different", "Error while storing the csv file in the output folder."
                , exception.getMessage());
    }
}