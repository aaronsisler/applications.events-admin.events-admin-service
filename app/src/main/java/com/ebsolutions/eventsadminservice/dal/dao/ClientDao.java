package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.config.DatabaseConstants;
import com.ebsolutions.eventsadminservice.dal.SortKeyType;
import com.ebsolutions.eventsadminservice.dal.dto.ClientDto;
import com.ebsolutions.eventsadminservice.dal.util.KeyBuilder;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.Client;
import com.ebsolutions.eventsadminservice.util.MetricsStopWatch;
import com.ebsolutions.eventsadminservice.util.UniqueIdGenerator;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.keyEqualTo;

@Slf4j
@Prototype
public class ClientDao {
    private final DynamoDbTable<ClientDto> ddbTable;

    public ClientDao(DynamoDbEnhancedClient enhancedClient) {
        this.ddbTable = enhancedClient.table(DatabaseConstants.DATABASE_TABLE_NAME, TableSchema.fromBean(ClientDto.class));
    }

    public Client read(String clientId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(SortKeyType.CLIENT.name(), SortKeyType.CLIENT, clientId);

            ClientDto clientDto = ddbTable.getItem(key);

            return clientDto == null
                    ? null
                    : Client.builder()
                    .clientId(StringUtils.remove(clientDto.getSortKey(), SortKeyType.CLIENT.name()))
                    .name(clientDto.getName())
                    .createdOn(clientDto.getCreatedOn())
                    .lastUpdatedOn(clientDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public List<Client> readAll() throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            List<ClientDto> clientDtos = ddbTable
                    .query(r -> r.queryConditional(
                            keyEqualTo(s -> s.partitionValue(SortKeyType.CLIENT.name()).build()))
                    )
                    .items()
                    .stream()
                    .toList();

            return clientDtos.stream()
                    .map(clientDto ->
                            Client.builder()
                                    .clientId(StringUtils.remove(clientDto.getSortKey(), SortKeyType.CLIENT.name()))
                                    .name(clientDto.getName())
                                    .createdOn(clientDto.getCreatedOn())
                                    .lastUpdatedOn(clientDto.getLastUpdatedOn())
                                    .build()
                    ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public Client create(Client client) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            LocalDateTime now = LocalDateTime.now();

            ClientDto clientDto = ClientDto.builder()
                    .partitionKey(SortKeyType.CLIENT.name())
                    .sortKey(SortKeyType.CLIENT + UniqueIdGenerator.generate())
                    .name(client.getName())
                    .createdOn(now)
                    .lastUpdatedOn(now)
                    .build();

            ddbTable.updateItem(clientDto);

            return Client.builder()
                    .clientId(clientDto.getPartitionKey())
                    .name(clientDto.getName())
                    .createdOn(clientDto.getCreatedOn())
                    .lastUpdatedOn(clientDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
        }
    }

    /**
     * This will replace the entire database object with the input client
     *
     * @param client the object to replace the current database object
     */
    public Client update(Client client) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            ClientDto clientDto = ClientDto.builder()
                    .partitionKey(SortKeyType.CLIENT.name())
                    .sortKey(SortKeyType.CLIENT + client.getClientId())
                    .name(client.getName())
                    .createdOn(client.getCreatedOn())
                    .lastUpdatedOn(LocalDateTime.now())
                    .build();

            ddbTable.putItem(clientDto);

            return Client.builder()
                    .clientId(StringUtils.remove(clientDto.getSortKey(), SortKeyType.CLIENT.name()))
                    .name(clientDto.getName())
                    .createdOn(clientDto.getCreatedOn())
                    .lastUpdatedOn(clientDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "update"));
        }
    }

    public void delete(String clientId) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(SortKeyType.CLIENT.name(), SortKeyType.CLIENT, clientId);

            ddbTable.deleteItem(key);
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "delete"));
        }
    }
}
