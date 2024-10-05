package com.ebsolutions.eventsadminservice.client;

import com.ebsolutions.eventsadminservice.model.Client;
import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("clients")
public class ClientController {
  private final ClientRepository clientRepository;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody List<@Valid Client> clients) {
    try {
      return ResponseEntity.ok(clientRepository.create(clients));
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(value = "/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@NotBlank @PathVariable String clientId) {
    try {
      Client client = clientRepository.read(clientId);

      return client != null ? ResponseEntity.ok(client) : ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAll() {
    try {
      List<Client> clients = clientRepository.readAll();

      return !clients.isEmpty() ? ResponseEntity.ok(clients) : ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handle(ConstraintViolationException constraintViolationException) {
    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
    String errorMessage;
    if (!violations.isEmpty()) {
      StringBuilder builder = new StringBuilder();
      violations.forEach(violation -> System.out.println(violation.getMessage()));
      violations.forEach(violation -> builder.append(" ").append(violation.getMessage()));
      errorMessage = builder.toString();
    } else {
      errorMessage = "ConstraintViolationException occurred.";
    }
    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }
}
