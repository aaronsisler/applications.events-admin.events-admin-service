package com.ebsolutions.eventsadminservice.client;

import com.ebsolutions.eventsadminservice.model.Client;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientRepository {
  private final ClientDao clientDao;

  public List<Client> create(List<Client> clients) {
    return clientDao.create(clients);
  }

  public Client read(String clientId) {
    return clientDao.read(clientId);
  }

  public List<Client> readAll() {
    return clientDao.readAll();
  }
}
