package com.ebsolutions.eventsadminservice.controller;

import com.ebsolutions.eventsadminservice.config.AllowableRequestMethod;
import com.ebsolutions.eventsadminservice.dal.dao.ClientDao;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.Client;
import com.ebsolutions.eventsadminservice.validator.RequestValidator;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import static io.micronaut.http.HttpResponse.*;

@Slf4j
@Controller("/clients")
public class ClientController {
    private final ClientDao clientDao;

    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Get(value = "/{clientId}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> get(@NotBlank @PathVariable String clientId) {
        try {
            Client client = clientDao.read(clientId);

            return client != null ? ok(client) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Post()
    public HttpResponse<?> post(@Valid @Body Client client) {
        try {
            if (!RequestValidator.isClientValid(AllowableRequestMethod.POST, client)) {
                return badRequest();
            }
            return ok(clientDao.create(client));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Put()
    public HttpResponse<?> put(@Valid @Body Client client) {
        try {
            if (!RequestValidator.isClientValid(AllowableRequestMethod.PUT, client)) {
                return badRequest();
            }

            return ok(clientDao.update(client));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }


    @Delete(value = "/{clientId}")
    public HttpResponse<?> delete(@jakarta.validation.constraints.NotBlank @PathVariable String clientId) {
        try {
            clientDao.delete(clientId);

            return noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }
}