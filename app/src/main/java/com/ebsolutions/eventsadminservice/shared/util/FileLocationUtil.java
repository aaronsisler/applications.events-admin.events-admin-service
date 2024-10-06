package com.ebsolutions.eventsadminservice.shared.util;

import java.text.MessageFormat;

public class FileLocationUtil {
  public static String build(String filePath, String filename, String fileType) {
    return MessageFormat.format("{0}/{1}.{2}", filePath, filename, fileType);
  }
}
