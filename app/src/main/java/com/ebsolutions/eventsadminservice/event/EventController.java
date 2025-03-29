package com.ebsolutions.eventsadminservice.event;

import com.ebsolutions.eventsadminservice.model.Event;
import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("establishments/{establishmentId}/events")
public class EventController {
  private final EventRepository eventRepository;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody List<@Valid Event> events) {
    try {
      return ResponseEntity.ok(eventRepository.create(events));
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(value = "/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@NotBlank @PathVariable String establishmentId,
                               @NotBlank @PathVariable String eventId) {
    try {
      Event event = eventRepository.read(establishmentId, eventId);

      return event != null ? ResponseEntity.ok(event) : ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAll(@NotBlank @PathVariable String establishmentId) {
    try {
      List<Event> events = eventRepository.readAll(establishmentId);

      return !events.isEmpty() ? ResponseEntity.ok(events) :
          ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> put(@RequestBody @Valid Event event) {
    try {
      return ResponseEntity.ok(eventRepository.update(event));
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @DeleteMapping(value = "/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@NotBlank @PathVariable String establishmentId,
                                  @NotBlank @PathVariable String eventId) {
    try {
      eventRepository.delete(establishmentId, eventId);

      return ResponseEntity.ok().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }
}
