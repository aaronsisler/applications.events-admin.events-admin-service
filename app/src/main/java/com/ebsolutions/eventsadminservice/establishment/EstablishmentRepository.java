package com.ebsolutions.eventsadminservice.establishment;

import com.ebsolutions.eventsadminservice.model.Establishment;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EstablishmentRepository {
  private final EstablishmentDao establishmentDao;

  public List<Establishment> create(List<Establishment> establishments) {
    return establishmentDao.create(establishments);
  }

  public Establishment read(String establishmentId) {
    return establishmentDao.read(establishmentId);
  }

  public List<Establishment> readAll() {
    return establishmentDao.readAll();
  }
}
