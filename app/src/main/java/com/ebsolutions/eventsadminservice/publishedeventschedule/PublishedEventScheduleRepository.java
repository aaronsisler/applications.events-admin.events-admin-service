package com.ebsolutions.eventsadminservice.publishedeventschedule;

import com.ebsolutions.eventsadminservice.model.PublishedEventSchedule;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PublishedEventScheduleRepository {
  private final PublishedEventScheduleDao publishedEventScheduleDao;

  public PublishedEventSchedule create(PublishedEventSchedule publishedEventSchedule) {
    return publishedEventScheduleDao.create(publishedEventSchedule);
  }

  public PublishedEventSchedule read(String establishmentId, String publishedEventScheduleId) {
    return publishedEventScheduleDao.read(establishmentId, publishedEventScheduleId);
  }

  public List<PublishedEventSchedule> readAll(String establishmentId) {
    return publishedEventScheduleDao.readAll(establishmentId);
  }
}
