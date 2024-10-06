package com.ebsolutions.eventsadminservice.eventschedule;

import com.ebsolutions.eventsadminservice.model.EventSchedule;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventScheduleRepository {
  private final EventScheduleDao eventScheduleDao;

  public List<EventSchedule> create(List<EventSchedule> eventSchedules) {
    return eventScheduleDao.create(eventSchedules);
  }

  public EventSchedule read(String clientId, String eventScheduleId) {
    return eventScheduleDao.read(clientId, eventScheduleId);
  }

  public List<EventSchedule> readAll(String clientId) {
    return eventScheduleDao.readAll(clientId);
  }

  public EventSchedule update(EventSchedule eventSchedule) {
    return eventScheduleDao.update(eventSchedule);
  }

  public void delete(String clientId, String eventScheduleId) {
    eventScheduleDao.delete(clientId, eventScheduleId);
  }
}
