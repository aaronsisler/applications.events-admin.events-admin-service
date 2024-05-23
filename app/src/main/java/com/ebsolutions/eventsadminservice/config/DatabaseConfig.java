package com.ebsolutions.eventsadminservice.config;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Slf4j
@Configuration
public class DatabaseConfig {
    private final String awsAccessKeyId = "accessKeyId";
    private final String awsSecretAccessKey = "secretAccessKey";

    @Getter
    @Value("${database.table_name:`Database table name not found in environment`}")
    public String databaseTable;

    @Value("${database.endpoint:`Database endpoint not found in environment`}")
    protected String endpoint;

    @Bean
    @Profile("local")
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