package com.ebsolutions.eventsadminservice.config;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import lombok.Getter;
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
    @Getter
    @Value("${database.table_name:`Database table name not found in environment`}")
    public String databaseTable;
    @Value("${database.endpoint:`Database endpoint not found in environment`}")
    protected String endpoint;

    @Prototype
    @Requires(env = {"local", "test", "dev"})
    public DynamoDbEnhancedClient localClientInstantiation() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(staticCredentialsProvider())
                .build();

        return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
    }

    private StaticCredentialsProvider staticCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey));
    }
}
