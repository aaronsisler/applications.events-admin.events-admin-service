package com.ebsolutions.eventsadminservice.scheduledevent;

import com.ebsolutions.eventsadminservice.model.ScheduledEvent;
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
@RequestMapping("event-schedules/{eventScheduleId}/scheduled-events")
public class ScheduledEventController {
  private final ScheduledEventRepository scheduledEventRepository;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@Valid @RequestBody List<@Valid ScheduledEvent> scheduledEvents) {
//    try {
    return ResponseEntity.ok(scheduledEventRepository.create(scheduledEvents));
//    } catch (DataProcessingException dpe) {
//      return ResponseEntity.internalServerError().body(dpe.getMessage());
//    }
  }

  @GetMapping(value = "/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@NotBlank @PathVariable String eventScheduleId,
                               @NotBlank @PathVariable String scheduledEventId) {
    try {
      ScheduledEvent scheduledEvents =
          scheduledEventRepository.read(eventScheduleId, scheduledEventId);

      return scheduledEvents != null ? ResponseEntity.ok(scheduledEvents) :
          ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAll(@NotBlank @PathVariable String eventScheduleId) {
    try {
      List<ScheduledEvent> scheduledEvents = scheduledEventRepository.readAll(eventScheduleId);

      return !scheduledEvents.isEmpty() ? ResponseEntity.ok(scheduledEvents) :
          ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> put(@RequestBody @Valid ScheduledEvent scheduledEvent) {
    try {
      return ResponseEntity.ok(scheduledEventRepository.update(scheduledEvent));
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @DeleteMapping(value = "/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@NotBlank @PathVariable String eventScheduleId,
                                  @NotBlank @PathVariable String scheduledEventId) {
    try {
      scheduledEventRepository.delete(eventScheduleId, scheduledEventId);

      return ResponseEntity.ok().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }
}
