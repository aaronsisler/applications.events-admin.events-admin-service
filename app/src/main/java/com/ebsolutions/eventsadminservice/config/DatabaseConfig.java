package com.ebsolutions.eventsadminservice.config;


import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DatabaseConfig {
  @Value("${storage.endpoint:`Database endpoint not found in environment`}")
  protected String endpoint;

  @Bean
  @Profile({"default"})
  public DynamoDbEnhancedClient defaultDynamoDbEnhancedClientInstantiation() {
    DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
        .credentialsProvider(ContainerCredentialsProvider.builder().build())
        .build();

    return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
  }

  @Bean
  @Profile({"local", "dev"})
  public DynamoDbEnhancedClient localDynamoDbEnhancedClientInstantiation() {
    DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
        .endpointOverride(URI.create(endpoint))
        .credentialsProvider(staticCredentialsProvider())
        .build();

    return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
  }


  private StaticCredentialsProvider staticCredentialsProvider() {
    String awsAccessKeyId = "accessKeyId";
    String awsSecretAccessKey = "secretAccessKey";

    return StaticCredentialsProvider.create(
        AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey));
  }
}