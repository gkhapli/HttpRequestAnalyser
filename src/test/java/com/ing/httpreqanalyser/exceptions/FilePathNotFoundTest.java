package com.ing.httpreqanalyser.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FilePathNotFoundTest {

    @Test
    public void testBuilder() {
        assertNotNull("Object is null", FilePathNotFound.builder().build());
    }

    @Test
    public void testGetters() {
        FilePathNotFound exception = FilePathNotFound.builder()
                .message("The provided file/directory path does not exist.").build();
        assertEquals("Message is different", "The provided file/directory path does not exist."
                , exception.getMessage());
    }
}