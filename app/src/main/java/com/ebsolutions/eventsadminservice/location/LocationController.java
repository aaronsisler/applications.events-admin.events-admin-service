package com.ebsolutions.eventsadminservice.location;

import com.ebsolutions.eventsadminservice.model.Location;
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
@RequestMapping("establishments/{establishmentId}/locations")
public class LocationController {
  private final LocationRepository locationRepository;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody List<@Valid Location> locations) {
    try {
      return ResponseEntity.ok(locationRepository.create(locations));
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(value = "/{locationId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@NotBlank @PathVariable String establishmentId,
                               @NotBlank @PathVariable String locationId) {
    try {
      Location location = locationRepository.read(establishmentId, locationId);

      return location != null ? ResponseEntity.ok(location) : ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAll(@NotBlank @PathVariable String establishmentId) {
    try {
      List<Location> locations = locationRepository.readAll(establishmentId);

      return !locations.isEmpty() ? ResponseEntity.ok(locations) :
          ResponseEntity.noContent().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> put(@RequestBody @Valid Location location) {
    try {
      return ResponseEntity.ok(locationRepository.update(location));
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }

  @DeleteMapping(value = "/{locationId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@NotBlank @PathVariable String establishmentId,
                                  @NotBlank @PathVariable String locationId) {
    try {
      locationRepository.delete(establishmentId, locationId);

      return ResponseEntity.ok().build();
    } catch (DataProcessingException dpe) {
      return ResponseEntity.internalServerError().body(dpe.getMessage());
    }
  }
}
