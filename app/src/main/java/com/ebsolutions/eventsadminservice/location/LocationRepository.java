package com.ebsolutions.eventsadminservice.location;

import com.ebsolutions.eventsadminservice.model.Location;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LocationRepository {
  private final LocationDao locationDao;

  public List<Location> create(List<Location> locations) {
    return locationDao.create(locations);
  }

  public Location read(String clientId, String locationId) {
    return locationDao.read(clientId, locationId);
  }

  public List<Location> readAll(String clientId) {
    return locationDao.readAll(clientId);
  }

  public Location update(Location location) {
    return locationDao.update(location);
  }

  public void delete(String clientId, String locationId) {
    locationDao.delete(clientId, locationId);
  }
}
