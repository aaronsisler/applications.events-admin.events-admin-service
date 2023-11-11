package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.config.DatabaseConstants;
import com.ebsolutions.eventsadminservice.dal.SortKeyType;
import com.ebsolutions.eventsadminservice.dal.dto.ClientDto;
import com.ebsolutions.eventsadminservice.dal.util.KeyBuilder;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.Client;
import com.ebsolutions.eventsadminservice.util.MetricsStopWatch;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.text.MessageFormat;

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
            Key key = KeyBuilder.build(clientId, SortKeyType.CLIENT);

            ClientDto clientDto = ddbTable.getItem(key);

//            return null;
            return clientDto == null
                    ? null
                    : new Client();
//                    : Client.builder();
//                    .clientId(clientDto.getPartitionKey())
//                    .name(clientDto.getName())
//                    .createdOn(clientDto.getCreatedOn())
//                    .lastUpdatedOn(clientDto.getLastUpdatedOn())
//                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }
}
