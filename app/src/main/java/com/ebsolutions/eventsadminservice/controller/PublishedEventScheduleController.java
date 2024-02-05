package com.ebsolutions.eventsadminservice.controller;

import com.ebsolutions.eventsadminservice.config.AllowableRequestMethod;
import com.ebsolutions.eventsadminservice.dal.dao.PublishedEventScheduleDao;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.PublishedEventSchedule;
import com.ebsolutions.eventsadminservice.service.PublishedEventScheduleOrchestrationService;
import com.ebsolutions.eventsadminservice.validator.RequestValidator;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.List;

import static io.micronaut.http.HttpResponse.*;

@Slf4j
@NoArgsConstructor
@Controller("/clients/{clientId}/published-event-schedules")
public class PublishedEventScheduleController {
    private PublishedEventScheduleDao publishedEventScheduleDao;
    private PublishedEventScheduleOrchestrationService publishedEventScheduleOrchestrationService;

    public PublishedEventScheduleController(PublishedEventScheduleDao publishedEventScheduleDao, PublishedEventScheduleOrchestrationService publishedEventScheduleOrchestrationService) {
        this.publishedEventScheduleDao = publishedEventScheduleDao;
        this.publishedEventScheduleOrchestrationService = publishedEventScheduleOrchestrationService;
    }

    @Get(value = "/{publishedEventScheduleId}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> get(@NotBlank @PathVariable String clientId, @NotBlank @PathVariable String publishedEventScheduleId) {
        try {
            PublishedEventSchedule publishedEventSchedule = publishedEventScheduleDao.read(clientId, publishedEventScheduleId);

            return publishedEventSchedule != null ? ok(publishedEventSchedule) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> getAll(@NotBlank @PathVariable String clientId) {
        try {
            List<PublishedEventSchedule> publishedEventSchedules = publishedEventScheduleDao.readAll(clientId);

            return !publishedEventSchedules.isEmpty() ? ok(publishedEventSchedules) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> post(@NotBlank @PathVariable String clientId, @Valid @Body PublishedEventSchedule publishedEventSchedule) {
        try {
            if (!RequestValidator.isPublishedEventScheduleValid(AllowableRequestMethod.POST, clientId, publishedEventSchedule)) {
                return badRequest();
            }

            return ok(publishedEventScheduleOrchestrationService.publishEventSchedule(publishedEventSchedule));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> put(@NotBlank @PathVariable String clientId, @Valid @Body PublishedEventSchedule publishedEventSchedule) {
        try {
            if (!RequestValidator.isPublishedEventScheduleValid(AllowableRequestMethod.PUT, clientId, publishedEventSchedule)) {
                return badRequest();
            }

            return ok(publishedEventScheduleDao.update(publishedEventSchedule));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }


    @Delete(value = "/{publishedEventScheduleId}")
    public HttpResponse<?> delete(@NotBlank @PathVariable String clientId, @NotBlank @PathVariable String publishedEventScheduleId) {
        try {
            publishedEventScheduleDao.delete(clientId, publishedEventScheduleId);

            return noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }
}