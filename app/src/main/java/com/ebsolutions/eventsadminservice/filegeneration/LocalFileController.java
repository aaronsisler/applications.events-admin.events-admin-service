package com.ebsolutions.eventsadminservice.filegeneration;

import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import jakarta.validation.constraints.NotBlank;
import java.net.URL;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
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
@Profile({"local", "dev"})
public class LocalFileController {
  private FileService fileService;

  @GetMapping(value = "/{filename}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getLocal(@NotBlank @PathVariable String establishmentId,
                                    @NotBlank @PathVariable String filename) {
    try {
      URL url = fileService.getLocal(establishmentId, filename);

      return url != null ? ResponseEntity.ok(url) : ResponseEntity.noContent().build();
    } catch (DataProcessingException dbe) {
      return ResponseEntity.internalServerError().body(dbe.getMessage());
    }
  }
}
