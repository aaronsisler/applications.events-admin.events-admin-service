package com.ebsolutions.eventsadminservice.publishedeventschedule;

import com.ebsolutions.eventsadminservice.filegeneration.FileGenerationOrchestrationService;
import com.ebsolutions.eventsadminservice.model.PublishedEventSchedule;
import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
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
@RequestMapping("establishments/{establishmentId}/published-event-schedules")
public class PublishedEventScheduleController {
  private final PublishedEventScheduleRepository publishedEventScheduleRepository;
  private final FileGenerationOrchestrationService fileGenerationOrchestrationService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody @Valid PublishedEventSchedule publishedEventSchedule) {
    try {
      return ResponseEntity.ok(
          fileGenerationOrchestrationService.publishEventSchedule(publishedEventSchedule));
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(value = "/{publishedEventScheduleId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@NotBlank @PathVariable String establishmentId,
                               @NotBlank @PathVariable String publishedEventScheduleId) {
    try {
      PublishedEventSchedule publishedEventSchedule =
          publishedEventScheduleRepository.read(establishmentId, publishedEventScheduleId);

      return publishedEventSchedule != null ? ResponseEntity.ok(publishedEventSchedule) :
          ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAll(@NotBlank @PathVariable String establishmentId) {
    try {
      List<PublishedEventSchedule> publishedEventSchedules =
          publishedEventScheduleRepository.readAll(establishmentId);

      return !publishedEventSchedules.isEmpty() ? ResponseEntity.ok(publishedEventSchedules) :
          ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }
}
