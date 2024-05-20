package com.ebsolutions.eventsadminservice.client;

import com.ebsolutions.eventsadminservice.model.Client;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {
    private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody List<Client> clients) {
        return ResponseEntity.ok(clientService.create(clients));
    }
}
