package com.ebsolutions.eventsadminservice.controller;

import com.ebsolutions.eventsadminservice.config.AllowableRequestMethod;
import com.ebsolutions.eventsadminservice.dal.dao.EventDao;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.Event;
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
@Controller("/clients/{clientId}/events")
public class EventController {
    @Inject
    private EventDao eventDao;

    @Get(value = "/{eventId}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> get(@NotBlank @PathVariable String clientId, @NotBlank @PathVariable String eventId) {
        try {
            Event event = eventDao.read(clientId, eventId);

            return event != null ? ok(event) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> getAll(@NotBlank @PathVariable String clientId) {
        try {
            List<Event> events = eventDao.readAll(clientId);

            return !events.isEmpty() ? ok(events) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> post(@NotBlank @PathVariable String clientId, @Valid @Body Event event) {
        try {
            if (!RequestValidator.isEventValid(AllowableRequestMethod.POST, clientId, event)) {
                return badRequest();
            }

            return ok(eventDao.create(event));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> put(@NotBlank @PathVariable String clientId, @Valid @Body Event event) {
        try {
            if (!RequestValidator.isEventValid(AllowableRequestMethod.PUT, clientId, event)) {
                return badRequest();
            }

            return ok(eventDao.update(event));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }


    @Delete(value = "/{eventId}")
    public HttpResponse<?> delete(@NotBlank @PathVariable String clientId, @NotBlank @PathVariable String eventId) {
        try {
            eventDao.delete(clientId, eventId);

            return noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }
}