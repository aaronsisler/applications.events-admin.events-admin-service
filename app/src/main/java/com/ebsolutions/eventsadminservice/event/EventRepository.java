package com.ebsolutions.eventsadminservice.event;

import com.ebsolutions.eventsadminservice.model.Event;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventRepository {
  private final EventDao eventDao;

  public List<Event> create(List<Event> events) {
    return eventDao.create(events);
  }

  public Event read(String establishmentId, String eventId) {
    return eventDao.read(establishmentId, eventId);
  }

  public List<Event> readAll(String establishmentId) {
    return eventDao.readAll(establishmentId);
  }

  public Event update(Event event) {
    return eventDao.update(event);
  }

  public void delete(String establishmentId, String eventId) {
    eventDao.delete(establishmentId, eventId);
  }
}
