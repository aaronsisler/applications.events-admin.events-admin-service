package com.ebsolutions.eventsadminservice.config;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.context.annotation.Requires;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Slf4j
@Factory
public class FileStorageConfig {
    private final String awsAccessKeyId = "access_key_id";
    private final String awsSecretAccessKey = "secret_access_key";
    private final String endpoint = "http://localhost:9090";

    @Prototype
    @Requires(env = {"local", "test"})
    public S3Client createLocal() {
        log.info("Here creating the Local S3 Config");
        URI localEndpoint = URI.create(endpoint);
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey);
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        return S3Client.builder().endpointOverride(localEndpoint).credentialsProvider(staticCredentialsProvider).build();
    }
}
