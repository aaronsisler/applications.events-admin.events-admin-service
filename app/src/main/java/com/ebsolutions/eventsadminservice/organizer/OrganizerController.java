package com.ebsolutions.eventsadminservice.organizer;

import com.ebsolutions.eventsadminservice.model.Organizer;
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
@RequestMapping("establishments/{establishmentId}/organizers")
public class OrganizerController {
  private final OrganizerRepository organizerRepository;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody List<@Valid Organizer> organizers) {
    try {
      return ResponseEntity.ok(organizerRepository.create(organizers));
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(value = "/{organizerId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@NotBlank @PathVariable String establishmentId,
                               @NotBlank @PathVariable String organizerId) {
    try {
      Organizer organizer = organizerRepository.read(establishmentId, organizerId);

      return organizer != null ? ResponseEntity.ok(organizer) : ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAll(@NotBlank @PathVariable String establishmentId) {
    try {
      List<Organizer> organizers = organizerRepository.readAll(establishmentId);

      return !organizers.isEmpty() ? ResponseEntity.ok(organizers) :
          ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> put(@RequestBody @Valid Organizer organizer) {
    try {
      return ResponseEntity.ok(organizerRepository.update(organizer));
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @DeleteMapping(value = "/{organizerId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@NotBlank @PathVariable String establishmentId,
                                  @NotBlank @PathVariable String organizerId) {
    try {
      organizerRepository.delete(establishmentId, organizerId);

      return ResponseEntity.ok().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }
}
