package com.ebsolutions.eventsadminservice.client;

import com.ebsolutions.eventsadminservice.model.Client;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ClientRepository {
  private final ClientDao clientDao;

  public ClientRepository(ClientDao clientDao) {
    this.clientDao = clientDao;
  }

  public List<Client> create(List<Client> clients) {
    return clientDao.create(clients);
  }
}
