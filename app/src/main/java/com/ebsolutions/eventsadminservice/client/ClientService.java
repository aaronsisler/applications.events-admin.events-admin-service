package com.ebsolutions.eventsadminservice.client;

import com.ebsolutions.eventsadminservice.model.Client;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientService {
    private final ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public List<Client> create(List<Client> clients) {
        return clientDao.create(clients);
    }
}
