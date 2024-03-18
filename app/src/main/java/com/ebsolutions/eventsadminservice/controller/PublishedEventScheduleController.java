package com.ebsolutions.eventsadminservice.controller;

import com.ebsolutions.eventsadminservice.config.AllowableRequestMethod;
import com.ebsolutions.eventsadminservice.dal.dao.PublishedEventScheduleDao;
import com.ebsolutions.eventsadminservice.exception.CsvGenerationException;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.exception.FileProcessingException;
import com.ebsolutions.eventsadminservice.model.PublishedEventSchedule;
import com.ebsolutions.eventsadminservice.service.PublishedEventScheduleOrchestrationService;
import com.ebsolutions.eventsadminservice.validator.RequestValidator;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import static io.micronaut.http.HttpResponse.*;

@Slf4j
@Controller("/clients/{clientId}/published-event-schedules")
public class PublishedEventScheduleController {
    private final PublishedEventScheduleDao publishedEventScheduleDao;
    private final PublishedEventScheduleOrchestrationService publishedEventScheduleOrchestrationService;

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

    @Post(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> post(@NotBlank @PathVariable String clientId, @Valid @Body PublishedEventSchedule publishedEventSchedule) {
        try {
            if (!RequestValidator.isPublishedEventScheduleValid(AllowableRequestMethod.POST, clientId, publishedEventSchedule)) {
                return badRequest();
            }

            return ok(publishedEventScheduleOrchestrationService.publishEventSchedule(publishedEventSchedule));
        } catch (CsvGenerationException | DataProcessingException | FileProcessingException e) {
            return serverError(e);
        }
    }
}