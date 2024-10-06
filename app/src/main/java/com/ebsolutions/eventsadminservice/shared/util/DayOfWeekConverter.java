package com.ebsolutions.eventsadminservice.shared.util;

import java.time.DayOfWeek;
import org.apache.commons.lang3.StringUtils;

public class DayOfWeekConverter {
  public static String convert(DayOfWeek dayOfWeek) {
    return dayOfWeek.name();
  }

  public static DayOfWeek convert(String dayOfWeek) {
    if (StringUtils.isBlank(dayOfWeek)) {
      return null;
    }

    return DayOfWeek.valueOf(dayOfWeek);
  }
}