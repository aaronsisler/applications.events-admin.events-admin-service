package com.ebsolutions.eventsadminservice.shared.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConstraintViolationExceptionHandler {
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handle(ConstraintViolationException constraintViolationException) {
    String errorMessage;
    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

    if (violations.isEmpty()) {
      errorMessage = "ConstraintViolationException occurred";

      return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    //TODO: The required fields aren't coming through to end user
    StringBuilder builder = new StringBuilder();
    violations.forEach(violation -> builder.append(" ").append(violation.getMessage()));
    errorMessage = builder.toString();

    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }
}
