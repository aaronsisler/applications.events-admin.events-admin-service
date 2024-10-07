package com.ebsolutions.eventsadminservice.shared.exception;

import java.io.Serial;

public class FileProcessingException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  public FileProcessingException(String errorMessage) {
    super(errorMessage);
  }
}
