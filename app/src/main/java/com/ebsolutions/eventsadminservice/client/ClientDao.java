package com.ebsolutions.eventsadminservice.client;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.keyEqualTo;

import com.ebsolutions.eventsadminservice.model.Client;
import com.ebsolutions.eventsadminservice.shared.Constants;
import com.ebsolutions.eventsadminservice.shared.RecordType;
import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.shared.util.KeyBuilder;
import com.ebsolutions.eventsadminservice.shared.util.MetricsStopwatch;
import com.ebsolutions.eventsadminservice.shared.util.UniqueIdGenerator;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteResult;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

@Slf4j
@Repository
@AllArgsConstructor
public class ClientDao {
  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

  public Client read(String clientId) throws DataProcessingException {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(RecordType.CLIENT.name(), RecordType.CLIENT, clientId);

      DynamoDbTable<ClientDto> clientDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(ClientDto.class));
      ClientDto clientDto = clientDtoDynamoDbTable.getItem(key);

      return clientDto == null
          ? null
          : Client.builder()
          .clientId(StringUtils.remove(clientDto.getSortKey(), RecordType.CLIENT.name()))
          .name(clientDto.getName())
          .createdOn(clientDto.getCreatedOn())
          .lastUpdatedOn(clientDto.getLastUpdatedOn())
          .build();
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopWatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
    }
  }

  public List<Client> readAll() throws DataProcessingException {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
    try {
      DynamoDbTable<ClientDto> clientDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(ClientDto.class));
      List<ClientDto> clientDtos = clientDtoDynamoDbTable
          .query(r -> r.queryConditional(
              keyEqualTo(s -> s.partitionValue(RecordType.CLIENT.name()).build()))
          )
          .items()
          .stream()
          .toList();

      return clientDtos.stream()
          .map(clientDto ->
              Client.builder()
                  .clientId(StringUtils.remove(clientDto.getSortKey(), RecordType.CLIENT.name()))
                  .name(clientDto.getName())
                  .createdOn(clientDto.getCreatedOn())
                  .lastUpdatedOn(clientDto.getLastUpdatedOn())
                  .build()
          ).collect(Collectors.toList());
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopWatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
    }
  }

  public List<Client> create(List<Client> clients) {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
    try {
      LocalDateTime now = LocalDateTime.now();

      List<ClientDto> clientDtos = new ArrayList<>();

      clients.forEach(client ->
          clientDtos.add(ClientDto.builder()
              .partitionKey(RecordType.CLIENT.name())
              .sortKey(RecordType.CLIENT + UniqueIdGenerator.generate())
              .name(client.getName())
              .createdOn(now)
              .lastUpdatedOn(now)
              .build())
      );

      DynamoDbTable<ClientDto> clientDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
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
              .clientId(StringUtils.remove(clientDto.getSortKey(), RecordType.CLIENT.name()))
              .name(clientDto.getName())
              .createdOn(clientDto.getCreatedOn())
              .lastUpdatedOn(clientDto.getLastUpdatedOn())
              .build()
      ).collect(Collectors.toList());

    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopWatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
    }
  }
}
