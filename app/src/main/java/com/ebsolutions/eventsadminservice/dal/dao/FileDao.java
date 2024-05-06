package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.config.Constants;
import com.ebsolutions.eventsadminservice.exception.FileProcessingException;
import com.ebsolutions.eventsadminservice.util.MetricsStopWatch;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URL;
import java.nio.ByteBuffer;
import java.text.MessageFormat;

@Slf4j
@Prototype
public class FileDao {

    private final S3Client s3Client;

    public FileDao(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void create(String fileLocation, ByteBuffer inputByteBuffer) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(Constants.FILE_STORAGE_LOCATION)
                    .key(fileLocation)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromByteBuffer(inputByteBuffer));
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new FileProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
        }
    }

    public URL read(String fileLocation) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();

        try {
            GetUrlRequest request = GetUrlRequest.builder()
                    .bucket(Constants.FILE_STORAGE_LOCATION)
                    .key(fileLocation)
                    .build();

            return s3Client.utilities().getUrl(request);
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new FileProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
        }
    }
}
