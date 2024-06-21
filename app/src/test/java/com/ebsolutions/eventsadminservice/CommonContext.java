package com.ebsolutions.eventsadminservice;

import com.ebsolutions.eventsadminservice.client.ClientController;
import com.ebsolutions.eventsadminservice.client.ClientDao;
import com.ebsolutions.eventsadminservice.client.ClientDto;
import com.ebsolutions.eventsadminservice.client.ClientService;
import com.ebsolutions.eventsadminservice.config.DatabaseConfig;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestConfiguration
public class CommonContext {
    @Bean
    public TableSchema<ClientDto> tableSchema() {
        return TableSchema.fromBean(ClientDto.class);
    }

    @Bean
    public DynamoDbTable<ClientDto> dynamoDbTable() {
        // TODO Fix IDE yelling
        DynamoDbTable<ClientDto> dynamoDbTable = Mockito.mock(DynamoDbTable.class);
        when(dynamoDbTable.tableName()).thenReturn("MOCK_TABLE_NAME");
        when(dynamoDbTable.tableSchema()).thenReturn(tableSchema());
        return dynamoDbTable;
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        DynamoDbEnhancedClient dynamoDbEnhancedClient = Mockito.mock(DynamoDbEnhancedClient.class);
        // TODO Fix IDE yelling
        when(dynamoDbEnhancedClient.table(any(String.class), any(TableSchema.class))).thenReturn(dynamoDbTable());

        return dynamoDbEnhancedClient;
    }

    @Bean
    public BatchWriteResult batchWriteResult() {
        return Mockito.mock(BatchWriteResult.class);
    }

    @Bean
    public DatabaseConfig databaseConfig() {
        DatabaseConfig databaseConfig = Mockito.mock(DatabaseConfig.class);
        when(databaseConfig.getTableName()).thenReturn("TEST_DATABASE_TABLE_NAME");
        return databaseConfig;
    }

    @Bean
    public ClientDao clientDao() {
        return new ClientDao(dynamoDbEnhancedClient(), databaseConfig());
    }

    @Bean
    public ClientService clientService() {
        return new ClientService(clientDao());
    }

    @Bean
    public ClientController clientController() {
        return new ClientController(clientService());
    }
}
