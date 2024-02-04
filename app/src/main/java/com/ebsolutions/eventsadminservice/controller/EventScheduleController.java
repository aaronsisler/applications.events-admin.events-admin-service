package com.ebsolutions.eventsadminservice.controller;

import com.ebsolutions.eventsadminservice.config.AllowableRequestMethod;
import com.ebsolutions.eventsadminservice.dal.dao.EventScheduleDao;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.EventSchedule;
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
@Controller("/clients/{clientId}/event-schedules")
public class EventScheduleController {
    @Inject
    private EventScheduleDao eventScheduleDao;

    @Get(value = "/{eventScheduleId}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> get(@NotBlank @PathVariable String clientId, @NotBlank @PathVariable String eventScheduleId) {
        try {
            EventSchedule eventSchedule = eventScheduleDao.read(clientId, eventScheduleId);

            return eventSchedule != null ? ok(eventSchedule) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> getAll(@NotBlank @PathVariable String clientId) {
        try {
            List<EventSchedule> eventSchedules = eventScheduleDao.readAll(clientId);

            return !eventSchedules.isEmpty() ? ok(eventSchedules) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> post(@NotBlank @PathVariable String clientId, @Valid @Body EventSchedule eventSchedule) {
        try {
            if (!RequestValidator.isEventScheduleValid(AllowableRequestMethod.POST, clientId, eventSchedule)) {
                return badRequest();
            }

            return ok(eventScheduleDao.create(eventSchedule));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> put(@NotBlank @PathVariable String clientId, @Valid @Body EventSchedule eventSchedule) {
        try {
            if (!RequestValidator.isEventScheduleValid(AllowableRequestMethod.PUT, clientId, eventSchedule)) {
                return badRequest();
            }

            return ok(eventScheduleDao.update(eventSchedule));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }


    @Delete(value = "/{eventScheduleId}")
    public HttpResponse<?> delete(@NotBlank @PathVariable String clientId, @NotBlank @PathVariable String eventScheduleId) {
        try {
            eventScheduleDao.delete(clientId, eventScheduleId);

            return noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }
}