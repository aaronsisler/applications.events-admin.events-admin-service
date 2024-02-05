package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.util.MetricsStopWatch;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.text.MessageFormat;

@Slf4j
@Prototype
public class FileStorageDao {

    private S3Client s3Client;
    private String bucketName = "event-admin-service-file-storage";

    public FileStorageDao(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public URI create(String clientId, File file) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            String keyName = String.join("/", clientId, file.getName());

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            s3Client.putObject(request, Path.of(file.toURI()));

            return URI.create(keyName);
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
        }
    }
}
