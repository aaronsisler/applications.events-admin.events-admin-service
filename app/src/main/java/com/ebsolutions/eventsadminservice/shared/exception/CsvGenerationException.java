package com.ebsolutions.eventsadminservice.shared.exception;

import java.io.Serial;

public class CsvGenerationException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  public CsvGenerationException(String errorMessage) {
    super(errorMessage);
  }
}
