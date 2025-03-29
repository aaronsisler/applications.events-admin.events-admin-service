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

  public Organizer read(String establishmentId, String organizerId) {
    return organizerDao.read(establishmentId, organizerId);
  }

  public List<Organizer> readAll(String establishmentId) {
    return organizerDao.readAll(establishmentId);
  }

  public Organizer update(Organizer organizer) {
    return organizerDao.update(organizer);
  }

  public void delete(String establishmentId, String organizerId) {
    organizerDao.delete(establishmentId, organizerId);
  }
}
