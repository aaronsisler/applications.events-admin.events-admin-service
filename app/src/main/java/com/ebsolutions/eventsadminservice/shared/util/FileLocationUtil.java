package com.ebsolutions.eventsadminservice.shared.util;

import java.text.MessageFormat;

public class FileLocationUtil {
  public static String build(String filePath, String filename) {
    return MessageFormat.format("{0}/{1}", filePath, filename);
  }
}
