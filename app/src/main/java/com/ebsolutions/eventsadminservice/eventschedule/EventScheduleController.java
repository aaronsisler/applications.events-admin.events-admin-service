package com.ebsolutions.eventsadminservice.eventschedule;

import com.ebsolutions.eventsadminservice.model.EventSchedule;
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
@RequestMapping("clients/{clientId}/event-schedules")
public class EventScheduleController {
  private final EventScheduleRepository eventScheduleRepository;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody List<@Valid EventSchedule> eventSchedules) {
    try {
      return ResponseEntity.ok(eventScheduleRepository.create(eventSchedules));
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(value = "/{eventScheduleId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@NotBlank @PathVariable String clientId,
                               @NotBlank @PathVariable String eventScheduleId) {
    try {
      EventSchedule eventSchedule = eventScheduleRepository.read(clientId, eventScheduleId);

      return eventSchedule != null ? ResponseEntity.ok(eventSchedule) :
          ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAll(@NotBlank @PathVariable String clientId) {
    try {
      List<EventSchedule> eventSchedules = eventScheduleRepository.readAll(clientId);

      return !eventSchedules.isEmpty() ? ResponseEntity.ok(eventSchedules) :
          ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> put(@RequestBody @Valid EventSchedule eventSchedule) {
    try {
      return ResponseEntity.ok(eventScheduleRepository.update(eventSchedule));
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @DeleteMapping(value = "/{eventScheduleId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@NotBlank @PathVariable String clientId,
                                  @NotBlank @PathVariable String eventScheduleId) {
    try {
      eventScheduleRepository.delete(clientId, eventScheduleId);

      return ResponseEntity.ok().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }
}
