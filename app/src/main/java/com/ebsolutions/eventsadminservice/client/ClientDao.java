package com.ebsolutions.eventsadminservice.client;

import com.ebsolutions.eventsadminservice.config.DatabaseConfig;
import com.ebsolutions.eventsadminservice.model.Client;
import com.ebsolutions.eventsadminservice.shared.MetricsStopwatch;
import com.ebsolutions.eventsadminservice.shared.SortKeyType;
import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.shared.util.UniqueIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteResult;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class ClientDao {
    private final DynamoDbTable<ClientDto> clientTable;
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    public ClientDao(DynamoDbEnhancedClient dynamoDbEnhancedClient, DatabaseConfig databaseConfig) {
        System.out.println("Table Name");
        System.out.println(databaseConfig.getTableName());
        this.clientTable = dynamoDbEnhancedClient.table(databaseConfig.getTableName(), TableSchema.fromBean(ClientDto.class));
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
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

            WriteBatch.Builder<ClientDto> writeBatchBuilder = WriteBatch.builder(ClientDto.class)
                    .mappedTableResource(clientTable);

            clientDtos.forEach(writeBatchBuilder::addPutItem);

            WriteBatch writeBatch = writeBatchBuilder.build();

            BatchWriteResult batchWriteResult = dynamoDbEnhancedClient.batchWriteItem(b -> b.writeBatches(writeBatch));


            if (!batchWriteResult.unprocessedPutItemsForTable(clientTable).isEmpty()) {
                batchWriteResult.unprocessedPutItemsForTable(clientTable).forEach(key ->
                        log.info(key.toString()));
            }

            List<Client> savedClients = new ArrayList<>();

            clientDtos.forEach(clientDto ->
                    savedClients.add(
                            Client.builder()
                                    .clientId(StringUtils.remove(clientDto.getSortKey(), SortKeyType.CLIENT.name()))
                                    .name(clientDto.getName())
                                    .createdOn(clientDto.getCreatedOn())
                                    .lastUpdatedOn(clientDto.getLastUpdatedOn())
                                    .build()
                    ));

            return savedClients;

        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
        }
    }
}
