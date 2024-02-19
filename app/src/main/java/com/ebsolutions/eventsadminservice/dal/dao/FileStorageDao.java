package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.util.MetricsStopWatch;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Prototype
public class FileStorageDao {

    private final S3Client s3Client;
    private String bucketName = "event-admin-service-file-storage";

    public FileStorageDao(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void create(String clientId) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();

        String objectKey = MessageFormat.format("{0}/{1}.csv", clientId, LocalDateTime.now().toString());

        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "test");
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .metadata(metadata)
                    .build();

//            s3Client.putObject(putObjectRequest, RequestBody.fromFile());

        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
        }
    }
}
