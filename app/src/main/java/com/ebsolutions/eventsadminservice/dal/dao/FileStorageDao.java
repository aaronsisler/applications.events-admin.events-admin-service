package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.util.MetricsStopWatch;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Slf4j
@Prototype
public class FileStorageDao {

    private final S3Client s3Client;
    private String bucketName = "event-admin-service-file-storage";

    public FileStorageDao(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void create(String clientId, byte[] fileBytes) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();

        String objectKey = MessageFormat.format("{0}/{1}.csv", clientId, LocalDateTime.now().toString());

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileBytes));
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
        }
    }
}
