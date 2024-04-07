package com.ebsolutions.eventsadminservice


import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.services.s3.S3Client

class FileStorageConfig {
    private final String awsAccessKeyId = "access_key_id";
    private final String awsSecretAccessKey = "secret_access_key";
    private final String endpoint = "http://localhost:9090";

    public S3Client localClientInstantiation() {
        log.info("Here creating the Local S3 Config");
        URI localEndpoint = URI.create(endpoint);
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey);
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        return S3Client.builder()
                .forcePathStyle(true)
                .endpointOverride(localEndpoint)
                .credentialsProvider(staticCredentialsProvider)
                .build();
    }
}
