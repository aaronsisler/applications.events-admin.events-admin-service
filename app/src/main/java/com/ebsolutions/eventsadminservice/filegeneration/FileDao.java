package com.ebsolutions.eventsadminservice.filegeneration;

import com.ebsolutions.eventsadminservice.shared.Constants;
import com.ebsolutions.eventsadminservice.shared.exception.FileProcessingException;
import com.ebsolutions.eventsadminservice.shared.util.MetricsStopwatch;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Slf4j
@Repository
@AllArgsConstructor
public class FileDao {
  private final S3Client s3Client;
  private final S3Presigner s3Presigner;

  public void create(String fileLocation, ByteBuffer inputByteBuffer) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();

    try {
      PutObjectRequest putObjectRequest = PutObjectRequest.builder()
          .bucket(Constants.FILE_STORAGE_LOCATION)
          .key(fileLocation)
          .build();

      s3Client.putObject(putObjectRequest, RequestBody.fromByteBuffer(inputByteBuffer));
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new FileProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopwatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
    }
  }

  public URL createPresignedUrl(String fileLocation) {
    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
        .bucket(Constants.FILE_STORAGE_LOCATION)
        .key(fileLocation)
        .build();
    GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
        .getObjectRequest(getObjectRequest)
        .signatureDuration(Duration.ofMinutes(1))
        .build();

    try (s3Presigner) {
      URL presignedUrl = s3Presigner.presignGetObject(getObjectPresignRequest).url();
      System.out.println(presignedUrl);
      return presignedUrl;
    } catch (Exception exception) {
      log.error("ERROR::{}", this.getClass().getName(), exception);
      throw new FileProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(),
              exception.getMessage()));
    }
  }
}
