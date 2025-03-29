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

  public EventSchedule read(String establishmentId, String eventScheduleId) {
    return eventScheduleDao.read(establishmentId, eventScheduleId);
  }

  public List<EventSchedule> readAll(String establishmentId) {
    return eventScheduleDao.readAll(establishmentId);
  }

  public EventSchedule update(EventSchedule eventSchedule) {
    return eventScheduleDao.update(eventSchedule);
  }

  public void delete(String establishmentId, String eventScheduleId) {
    eventScheduleDao.delete(establishmentId, eventScheduleId);
  }
}
