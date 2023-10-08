package com.ebsolutions.eventsadminservice.controllers.data;

import com.ebsolutions.eventsadminservice.dal.daos.ClientDao;
import com.ebsolutions.eventsadminservice.exceptions.DataProcessingException;
import com.ebsolutions.eventsadminservice.models.Client2;
import com.ebsolutions.eventsadminservice.validators.LocalDateValidator;
import com.ebsolutions.eventsadminservice.validators.StringValidator;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

import static io.micronaut.http.HttpResponse.*;

@Slf4j
@Controller("/data/clients")
public class ClientController {
    private final ClientDao clientDao;

    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Get(value = "/{clientId}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> get(@NotBlank @PathVariable String clientId) {
        try {
            Client2 client = clientDao.read(clientId);

            return client != null ? ok(client) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Post()
    public HttpResponse<?> post(@Valid @Body Client2 client) {
        try {
            return ok(clientDao.create(client));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Put()
    public HttpResponse<?> put(@Valid @Body Client2 client) {
        try {
            if (StringValidator.isBlank(client.getClientId())
                    || !LocalDateValidator.isBeforeNow(client.getCreatedOn())
            ) {
                return badRequest();
            }

            return ok(clientDao.update(client));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }


    @Delete(value = "/{clientId}")
    public HttpResponse<?> delete(@NotBlank @PathVariable String clientId) {
        try {
            clientDao.delete(clientId);

            return noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }
}
