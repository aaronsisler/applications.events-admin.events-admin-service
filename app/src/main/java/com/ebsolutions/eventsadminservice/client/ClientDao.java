package com.ebsolutions.eventsadminservice.client;

import com.ebsolutions.eventsadminservice.config.DatabaseConfig;
import com.ebsolutions.eventsadminservice.model.Client;
import com.ebsolutions.eventsadminservice.shared.MetricsStopwatch;
import com.ebsolutions.eventsadminservice.shared.SortKeyType;
import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.shared.util.UniqueIdGenerator;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteResult;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

@Slf4j
@Repository
public class ClientDao {
  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
  private final DatabaseConfig databaseConfig;

  public ClientDao(DynamoDbEnhancedClient dynamoDbEnhancedClient, DatabaseConfig databaseConfig) {
    this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
    this.databaseConfig = databaseConfig;
  }

  public List<Client> create(List<Client> clients) {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
    try {
      LocalDateTime now = LocalDateTime.now();

      List<ClientDto> clientDtos = new ArrayList<>();

      clients.forEach(client ->
          clientDtos.add(ClientDto.builder()
              .partitionKey(SortKeyType.CLIENT.name())
              .sortKey(SortKeyType.CLIENT + UniqueIdGenerator.generate())
              .name(client.getName())
              .createdOn(now)
              .lastUpdatedOn(now)
              .build())
      );

      DynamoDbTable<ClientDto> clientDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(databaseConfig.getTableName(),
              TableSchema.fromBean(ClientDto.class));
      WriteBatch.Builder<ClientDto> writeBatchBuilder = WriteBatch.builder(ClientDto.class)
          .mappedTableResource(clientDtoDynamoDbTable);

      clientDtos.forEach(writeBatchBuilder::addPutItem);

      WriteBatch writeBatch = writeBatchBuilder.build();

      BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest
          .builder()
          .addWriteBatch(writeBatch)
          .build();

      BatchWriteResult batchWriteResult =
          dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest);

      if (!batchWriteResult.unprocessedPutItemsForTable(clientDtoDynamoDbTable).isEmpty()) {
        batchWriteResult.unprocessedPutItemsForTable(clientDtoDynamoDbTable).forEach(key ->
            log.info(key.toString()));
      }

      return clientDtos.stream().map(clientDto ->
          Client.builder()
              .clientId(StringUtils.remove(clientDto.getSortKey(), SortKeyType.CLIENT.name()))
              .name(clientDto.getName())
              .createdOn(clientDto.getCreatedOn())
              .lastUpdatedOn(clientDto.getLastUpdatedOn())
              .build()
      ).collect(Collectors.toList());

    } catch (Exception e) {
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopWatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
    }
  }
}
