package com.ebsolutions.eventsadminservice.establishment;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.sortBeginsWith;

import com.ebsolutions.eventsadminservice.model.Establishment;
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
public class EstablishmentDao {
  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

  public Establishment read(String establishmentId) throws DataProcessingException {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(RecordType.ESTABLISHMENT.name(), RecordType.ESTABLISHMENT,
          establishmentId);

      DynamoDbTable<EstablishmentDto> establishmentDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EstablishmentDto.class));
      EstablishmentDto establishmentDto = establishmentDtoDynamoDbTable.getItem(key);

      return establishmentDto == null
          ? null
          : Establishment.builder()
          .establishmentId(StringUtils.remove(establishmentDto.getSortKey(),
              RecordType.ESTABLISHMENT.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
          .name(establishmentDto.getName())
          .createdOn(establishmentDto.getCreatedOn())
          .lastUpdatedOn(establishmentDto.getLastUpdatedOn())
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

  public List<Establishment> readAll() throws DataProcessingException {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
    try {
      DynamoDbTable<EstablishmentDto> establishmentDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EstablishmentDto.class));

      List<EstablishmentDto> establishmentDtos = establishmentDtoDynamoDbTable
          .query(r -> r.queryConditional(
              sortBeginsWith(s
                  -> s.partitionValue(RecordType.ESTABLISHMENT.name())
                  .sortValue(
                      RecordType.ESTABLISHMENT.name()
                          .concat(Constants.DATABASE_RECORD_TYPE_DELIMITER))
                  .build()))
          )
          .items()
          .stream()
          .toList();

      return establishmentDtos.stream()
          .map(establishmentDto ->
              Establishment.builder()
                  .establishmentId(StringUtils.remove(establishmentDto.getSortKey(),
                      RecordType.ESTABLISHMENT.name()
                          .concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
                  .name(establishmentDto.getName())
                  .createdOn(establishmentDto.getCreatedOn())
                  .lastUpdatedOn(establishmentDto.getLastUpdatedOn())
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

  public List<Establishment> create(List<Establishment> establishments) {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
    try {
      LocalDateTime now = LocalDateTime.now();

      List<EstablishmentDto> establishmentDtos = new ArrayList<>();

      establishments.forEach(establishment ->
          establishmentDtos.add(EstablishmentDto.builder()
              .partitionKey(RecordType.ESTABLISHMENT.name())
              .sortKey(RecordType.ESTABLISHMENT +
                  Constants.DATABASE_RECORD_TYPE_DELIMITER +
                  UniqueIdGenerator.generate())
              .name(establishment.getName())
              .createdOn(now)
              .lastUpdatedOn(now)
              .build())
      );

      DynamoDbTable<EstablishmentDto> establishmentDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EstablishmentDto.class));
      WriteBatch.Builder<EstablishmentDto> writeBatchBuilder =
          WriteBatch.builder(EstablishmentDto.class)
              .mappedTableResource(establishmentDtoDynamoDbTable);

      establishmentDtos.forEach(writeBatchBuilder::addPutItem);

      WriteBatch writeBatch = writeBatchBuilder.build();

      BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest
          .builder()
          .addWriteBatch(writeBatch)
          .build();

      BatchWriteResult batchWriteResult =
          dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest);

      if (!batchWriteResult.unprocessedPutItemsForTable(establishmentDtoDynamoDbTable).isEmpty()) {
        batchWriteResult.unprocessedPutItemsForTable(establishmentDtoDynamoDbTable).forEach(key ->
            log.info(key.toString()));
      }

      return establishmentDtos.stream().map(establishmentDto ->
          Establishment.builder()
              .establishmentId(StringUtils.remove(establishmentDto.getSortKey(),
                  RecordType.ESTABLISHMENT.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
              .name(establishmentDto.getName())
              .createdOn(establishmentDto.getCreatedOn())
              .lastUpdatedOn(establishmentDto.getLastUpdatedOn())
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
