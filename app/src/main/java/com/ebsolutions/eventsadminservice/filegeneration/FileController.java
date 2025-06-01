package com.ebsolutions.eventsadminservice.filegeneration;

import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import jakarta.validation.constraints.NotBlank;
import java.net.URL;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("establishments/{establishmentId}/files")
public class FileController {
  private FileService fileService;

  @GetMapping(value = "/{filename}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@NotBlank @PathVariable String establishmentId,
                               @NotBlank @PathVariable String filename) {
    try {
      URL url = fileService.get(establishmentId, filename);

      return url != null ? ResponseEntity.ok(url) : ResponseEntity.noContent().build();
    } catch (DataProcessingException dbe) {
      return ResponseEntity.internalServerError().body(dbe.getMessage());
    }
  }
}
