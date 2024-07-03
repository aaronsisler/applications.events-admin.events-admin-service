package com.ebsolutions.eventsadminservice.shared.exception;


public class DataProcessingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataProcessingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}