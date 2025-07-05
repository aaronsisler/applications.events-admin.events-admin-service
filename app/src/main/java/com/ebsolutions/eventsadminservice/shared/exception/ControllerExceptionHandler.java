package com.ebsolutions.eventsadminservice.shared.exception;

import com.ebsolutions.eventsadminservice.model.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {


  /**
   * @param httpMessageNotReadableException caught in controller as thrown from service
   * @return custom response with descriptive error messages
   */
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400",
          content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          }),
  })

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleParseException(
      HttpMessageNotReadableException httpMessageNotReadableException) {

    return ResponseEntity.badRequest()
        .body(
            ErrorResponse.builder()
                .messages(Collections.singletonList(
                    httpMessageNotReadableException.getMostSpecificCause().getMessage()))
                .build()
        );
  }

  /**
   * @param constraintViolationException caught in controller as thrown from service
   * @return custom response with descriptive error messages
   */
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400",
          content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          }),
  })
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handle(
      ConstraintViolationException constraintViolationException) {
    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

    if (violations.isEmpty()) {
      return ResponseEntity.badRequest()
          .body(
              ErrorResponse.builder()
                  .messages(
                      Collections.singletonList(
                          "A mandatory field is missing or something went wrong"))
                  .build()
          );
    }

    List<String> messages = new ArrayList<>();

    violations.forEach(violation ->
        messages.add(
            violation.getPropertyPath().toString()
                .concat("::")
                .concat(violation.getMessage()))
    );

    return ResponseEntity.badRequest()
        .body(
            ErrorResponse.builder()
                .messages(messages)
                .build()
        );
  }
}
