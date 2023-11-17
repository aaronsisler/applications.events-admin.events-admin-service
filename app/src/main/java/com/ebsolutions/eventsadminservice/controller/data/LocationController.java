package com.ebsolutions.eventsadminservice.controller.data;

import com.ebsolutions.eventsadminservice.controller.RequestMethod;
import com.ebsolutions.eventsadminservice.dal.dao.LocationDao;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.Location;
import com.ebsolutions.eventsadminservice.validator.RequestValidator;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import static io.micronaut.http.HttpResponse.*;

@Slf4j
@AllArgsConstructor
@Controller("/data/locations")
public class LocationController {
    private final LocationDao locationDao;

    @Get(value = "/{locationId}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> get(@NotBlank @PathVariable String locationId) {
        try {
            Location location = locationDao.read(locationId);

            return location != null ? ok(location) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Post()
    public HttpResponse<?> post(@Valid @Body Location location) {
        try {
            return ok(locationDao.create(location));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }

    @Put()
    public HttpResponse<?> put(@Valid @Body Location location) {
        try {
            if (!RequestValidator.isLocationValid(RequestMethod.PUT, location)) {
                return badRequest();
            }

            return ok(locationDao.update(location));
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }


    @Delete(value = "/{locationId}")
    public HttpResponse<?> delete(@NotBlank @PathVariable String locationId) {
        try {
            locationDao.delete(locationId);

            return noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }
}