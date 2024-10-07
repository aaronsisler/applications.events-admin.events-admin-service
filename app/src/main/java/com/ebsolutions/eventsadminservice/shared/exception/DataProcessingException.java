package com.ebsolutions.eventsadminservice.shared.exception;


import java.io.Serial;

public class DataProcessingException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  public DataProcessingException(String errorMessage) {
    super(errorMessage);
  }
}