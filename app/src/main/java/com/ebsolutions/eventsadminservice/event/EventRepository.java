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

  public Event read(String clientId, String eventId) {
    return eventDao.read(clientId, eventId);
  }

  public List<Event> readAll(String clientId) {
    return eventDao.readAll(clientId);
  }

  public Event update(Event event) {
    return eventDao.update(event);
  }

  public void delete(String clientId, String eventId) {
    eventDao.delete(clientId, eventId);
  }
}
