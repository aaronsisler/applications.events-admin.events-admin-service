package com.ebsolutions.eventsadminservice.tooling;

import com.ebsolutions.eventsadminservice.client.ClientController;
import com.ebsolutions.eventsadminservice.client.ClientDao;
import com.ebsolutions.eventsadminservice.client.ClientService;
import com.ebsolutions.eventsadminservice.config.DatabaseConfig;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableMetadata;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestConfiguration
public class CommonContext {
    @Bean
    public TableMetadata tableMetadata() {
        return Mockito.mock(TableMetadata.class);
    }

    @Bean
    public TableSchema tableSchema() {
        TableSchema tableSchema = Mockito.mock(TableSchema.class);
        when(tableSchema.tableMetadata()).thenReturn(tableMetadata());
        return tableSchema;
    }

    @Bean
    public DynamoDbTable<Object> dynamoDbTable() {
        DynamoDbTable<Object> dynamoDbTable = Mockito.mock(DynamoDbTable.class);
        when(dynamoDbTable.tableName()).thenReturn("MOCK_TABLE_NAME");
        when(dynamoDbTable.tableSchema()).thenReturn(tableSchema());
        return dynamoDbTable;
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        System.out.println("Test client!");
        DynamoDbEnhancedClient dynamoDbEnhancedClient = Mockito.mock(DynamoDbEnhancedClient.class);
        when(dynamoDbEnhancedClient.table(any(String.class), any())).thenReturn(dynamoDbTable());
        when(dynamoDbEnhancedClient.table(any(String.class), any())).thenReturn(dynamoDbTable());

        return dynamoDbEnhancedClient;
    }

    @Bean
    public BatchWriteResult batchWriteResult() {
        return Mockito.mock(BatchWriteResult.class);
    }

    @Bean
    public DatabaseConfig databaseConfig() {
        System.out.println("Test DatabaseConfig!");
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
