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

  public Location read(String establishmentId, String locationId) {
    return locationDao.read(establishmentId, locationId);
  }

  public List<Location> readAll(String establishmentId) {
    return locationDao.readAll(establishmentId);
  }

  public Location update(Location location) {
    return locationDao.update(location);
  }

  public void delete(String establishmentId, String locationId) {
    locationDao.delete(establishmentId, locationId);
  }
}
