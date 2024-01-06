package com.ebsolutions.eventsadminservice.controller;

import com.ebsolutions.eventsadminservice.config.AllowableRequestMethod;
import com.ebsolutions.eventsadminservice.dal.dao.ScheduledEventDao;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.ScheduledEvent;
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
@Controller("/clients/{clientId}/scheduled-events")
public class ScheduledEventController {
    @Inject
    private ScheduledEventDao scheduledEventDao;

    @Get(value = "/{scheduledEventId}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> get(@NotBlank @PathVariable String clientId, @NotBlank @PathVariable String scheduledEventId) {
        try {
            ScheduledEvent scheduledEvent = scheduledEventDao.read(clientId, scheduledEventId);

            return scheduledEvent != null ? ok(scheduledEvent) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> getAll(@NotBlank @PathVariable String clientId) {
        try {
            List<ScheduledEvent> scheduledEvents = scheduledEventDao.readAll(clientId);

            return !scheduledEvents.isEmpty() ? ok(scheduledEvents) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> post(@NotBlank @PathVariable String clientId, @Valid @Body ScheduledEvent scheduledEvent) {
        try {
            if (!RequestValidator.isScheduledEventValid(AllowableRequestMethod.POST, clientId, scheduledEvent)) {
                return badRequest();
            }

            return ok(scheduledEventDao.create(scheduledEvent));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> put(@NotBlank @PathVariable String clientId, @Valid @Body ScheduledEvent scheduledEvent) {
        try {
            if (!RequestValidator.isScheduledEventValid(AllowableRequestMethod.PUT, clientId, scheduledEvent)) {
                return badRequest();
            }

            return ok(scheduledEventDao.update(scheduledEvent));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }


    @Delete(value = "/{scheduledEventId}")
    public HttpResponse<?> delete(@NotBlank @PathVariable String clientId, @NotBlank @PathVariable String scheduledEventId) {
        try {
            scheduledEventDao.delete(clientId, scheduledEventId);

            return noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }
}