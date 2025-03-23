package com.ebsolutions.eventsadminservice.config;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class FileStorageConfig {
  @Value("${storage.endpoint:`File storage endpoint not found in environment`}")
  protected String endpoint;

  @Bean
  @Profile({"default"})
  public S3Client defaultS3ClientInstantiation() {
    return S3Client.builder()
        .build();
  }

  @Bean
  @Profile({"local", "dev"})
  public S3Client localS3ClientInstantiation() {
    return S3Client.builder()
        .forcePathStyle(true)
        .endpointOverride(URI.create(endpoint))
        .credentialsProvider(staticCredentialsProvider())
        .build();
  }

  private StaticCredentialsProvider staticCredentialsProvider() {
    String awsAccessKeyId = "accessKeyId";
    String awsSecretAccessKey = "secretAccessKey";

    return StaticCredentialsProvider.create(
        AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey));
  }
}
