package com.ebsolutions.eventsadminservice.exception;

public class FileProcessingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileProcessingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
