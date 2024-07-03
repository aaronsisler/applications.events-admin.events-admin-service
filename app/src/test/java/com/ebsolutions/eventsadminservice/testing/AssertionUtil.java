package com.ebsolutions.eventsadminservice.testing;

import com.ebsolutions.eventsadminservice.model.Client;
import com.ebsolutions.eventsadminservice.shared.SortKeyType;
import com.ebsolutions.eventsadminservice.utils.DateTimeComparisonUtil;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertionUtil {
    public static void assertWrittenClient(Client expectedClient, WriteRequest writeRequest) {
        assertEquals(SortKeyType.CLIENT.name(), writeRequest.putRequest().item().get("partitionKey").s());
        assertEquals(SortKeyType.CLIENT + expectedClient.getClientId(), writeRequest.putRequest().item().get("sortKey").s());
        assertEquals(expectedClient.getName(), writeRequest.putRequest().item().get("name").s());
        assertTrue(DateTimeComparisonUtil.areDateTimesEqual(expectedClient.getCreatedOn(), LocalDateTime.parse(writeRequest.putRequest().item().get("createdOn").s())));
        assertTrue(DateTimeComparisonUtil.areDateTimesEqual(expectedClient.getLastUpdatedOn(), LocalDateTime.parse(writeRequest.putRequest().item().get("lastUpdatedOn").s())));
    }
}
