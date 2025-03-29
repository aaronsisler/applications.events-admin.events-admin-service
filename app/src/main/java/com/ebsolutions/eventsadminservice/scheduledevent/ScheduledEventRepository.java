package com.ebsolutions.eventsadminservice.scheduledevent;

import com.ebsolutions.eventsadminservice.model.ScheduledEvent;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduledEventRepository {
  private final ScheduledEventDao scheduledEventDao;

  public List<ScheduledEvent> create(List<ScheduledEvent> scheduledEvents) {
    return scheduledEventDao.create(scheduledEvents);
  }

  public ScheduledEvent read(String eventScheduleId, String scheduledEventId) {
    return scheduledEventDao.read(eventScheduleId, scheduledEventId);
  }

  public List<ScheduledEvent> readAll(String establishmentId) {
    return scheduledEventDao.readAll(establishmentId);
  }

  public ScheduledEvent update(ScheduledEvent scheduledEvent) {
    return scheduledEventDao.update(scheduledEvent);
  }

  public void delete(String eventScheduleId, String scheduledEventId) {
    scheduledEventDao.delete(eventScheduleId, scheduledEventId);
  }
}
