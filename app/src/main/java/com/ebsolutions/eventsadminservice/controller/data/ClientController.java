package com.ebsolutions.eventsadminservice.controller.data;

import com.ebsolutions.eventsadminservice.config.Constants;
import com.ebsolutions.eventsadminservice.dal.dao.ClientDao;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.Client;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import static io.micronaut.http.HttpResponse.*;

@Slf4j
@AllArgsConstructor
@Controller("/" + Constants.URL_PATH_DATA + "/clients")
public class ClientController {
    private final ClientDao clientDao;

    @Get(value = "/{clientId}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> get(@NotBlank @PathVariable String clientId) {
        try {
            Client client = clientDao.read(clientId);

            return client != null ? ok(client) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }
}