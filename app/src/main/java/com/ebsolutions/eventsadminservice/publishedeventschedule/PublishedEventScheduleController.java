package com.ebsolutions.eventsadminservice.publishedeventschedule;

import com.ebsolutions.eventsadminservice.model.PublishedEventSchedule;
import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("clients/{clientId}/published-event-schedules")
public class PublishedEventScheduleController {
  private final PublishedEventScheduleRepository publishedEventScheduleRepository;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody @Valid PublishedEventSchedule publishedEventSchedule) {
    try {
      return ResponseEntity.ok(publishedEventScheduleRepository.create(publishedEventSchedule));
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(value = "/{publishedEventScheduleId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@NotBlank @PathVariable String clientId,
                               @NotBlank @PathVariable String publishedEventScheduleId) {
    try {
      PublishedEventSchedule publishedEventSchedule =
          publishedEventScheduleRepository.read(clientId, publishedEventScheduleId);

      return publishedEventSchedule != null ? ResponseEntity.ok(publishedEventSchedule) :
          ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }
}
