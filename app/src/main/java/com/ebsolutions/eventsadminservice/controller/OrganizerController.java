package com.ebsolutions.eventsadminservice.controller;

import com.ebsolutions.eventsadminservice.config.AllowableRequestMethod;
import com.ebsolutions.eventsadminservice.dal.dao.OrganizerDao;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.Organizer;
import com.ebsolutions.eventsadminservice.validator.RequestValidator;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.List;

import static io.micronaut.http.HttpResponse.*;

@Slf4j
@NoArgsConstructor
@Controller("/clients/{clientId}/organizers")
public class OrganizerController {
    @Inject
    private OrganizerDao organizerDao;

    @Get(value = "/{organizerId}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> get(@NotBlank @PathVariable String clientId, @NotBlank @PathVariable String organizerId) {
        try {
            Organizer organizer = organizerDao.read(clientId, organizerId);

            return organizer != null ? ok(organizer) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> getAll(@NotBlank @PathVariable String clientId) {
        try {
            List<Organizer> organizers = organizerDao.readAll(clientId);

            return !organizers.isEmpty() ? ok(organizers) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> post(@NotBlank @PathVariable String clientId, @Valid @Body Organizer organizer) {
        try {
            if (!RequestValidator.isOrganizerValid(AllowableRequestMethod.POST, clientId, organizer)) {
                return badRequest();
            }

            return ok(organizerDao.create(organizer));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> put(@NotBlank @PathVariable String clientId, @Valid @Body Organizer organizer) {
        try {
            if (!RequestValidator.isOrganizerValid(AllowableRequestMethod.PUT, clientId, organizer)) {
                return badRequest();
            }

            return ok(organizerDao.update(organizer));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }


    @Delete(value = "/{organizerId}")
    public HttpResponse<?> delete(@NotBlank @PathVariable String clientId, @NotBlank @PathVariable String organizerId) {
        try {
            organizerDao.delete(clientId, organizerId);

            return noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }
}