package com.ebsolutions.eventsadminservice.config;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Slf4j
@Factory
public class FileStorageConfig {
    private final String awsAccessKeyId = "accessKeyId";
    private final String awsSecretAccessKey = "secretAccessKey";
    @Value("${file_location.endpoint:`File storage endpoint not found in environment`}")
    protected String endpoint;

    @Prototype
    @Requires(env = {"local", "test", "dev"})
    public S3Client dockerClientInstantiation() {
        return S3Client.builder()
                .forcePathStyle(true)
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(staticCredentialsProvider())
                .build();
    }

    @Prototype
    @Requires(env = {"beta", "prod"})
    public S3Client cloudClientInstantiation() {
        return S3Client.builder().build();
    }

    private StaticCredentialsProvider staticCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey));
    }
}
