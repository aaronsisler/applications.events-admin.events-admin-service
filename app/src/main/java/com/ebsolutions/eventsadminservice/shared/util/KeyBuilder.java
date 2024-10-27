package com.ebsolutions.eventsadminservice.shared.util;

import com.ebsolutions.eventsadminservice.shared.RecordType;
import software.amazon.awssdk.enhanced.dynamodb.Key;

public class KeyBuilder {
  public static Key.Builder builder(String partitionKey, RecordType recordType, String sortKey) {
    return Key.builder()
        .partitionValue(partitionKey)
        .sortValue(recordType.name() + sortKey);
  }

  public static Key build(String partitionKey, RecordType recordType, String sortKey) {
    return builder(partitionKey, recordType, sortKey).build();
  }
}
