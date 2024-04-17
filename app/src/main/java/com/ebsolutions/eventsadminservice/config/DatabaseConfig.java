package com.ebsolutions.eventsadminservice.config;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.context.annotation.Requires;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Slf4j
@Factory
public class DatabaseConfig {
    private final String awsAccessKeyId = "accessKeyId";
    private final String awsSecretAccessKey = "secretAccessKey";
    private final String endpointLocal = "http://localhost:8000";
    private final String endpointDocker = "http://host.docker.internal:8000";

    @Prototype
    @Requires(env = {"local", "test"})
    public DynamoDbEnhancedClient localClientInstantiation() {
        StaticCredentialsProvider staticCredentialsProvider = staticCredentialsProvider();
        URI localEndpoint = URI.create(endpointLocal);
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .endpointOverride(localEndpoint)
                .credentialsProvider(staticCredentialsProvider)
                .build();

        return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
    }

    @Prototype
    @Requires(env = {"dev"})
    public DynamoDbEnhancedClient dockerClientInstantiation() {
        StaticCredentialsProvider staticCredentialsProvider = staticCredentialsProvider();
        URI localEndpoint = URI.create(endpointDocker);
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .endpointOverride(localEndpoint)
                .credentialsProvider(staticCredentialsProvider)
                .build();

        return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
    }

    private StaticCredentialsProvider staticCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey));
    }
}
