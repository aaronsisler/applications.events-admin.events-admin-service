package com.ebsolutions.eventsadminservice.organizer;

import com.ebsolutions.eventsadminservice.model.Organizer;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrganizerRepository {
  private final OrganizerDao organizerDao;

  public List<Organizer> create(List<Organizer> organizers) {
    return organizerDao.create(organizers);
  }

  public Organizer read(String clientId, String organizerId) {
    return organizerDao.read(clientId, organizerId);
  }

  public List<Organizer> readAll(String clientId) {
    return organizerDao.readAll(clientId);
  }

  public Organizer update(Organizer organizer) {
    return organizerDao.update(organizer);
  }

  public void delete(String clientId, String organizerId) {
    organizerDao.delete(clientId, organizerId);
  }
}
