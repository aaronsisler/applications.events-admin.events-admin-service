package com.ebsolutions.eventsadminservice.controller;

import com.ebsolutions.eventsadminservice.dal.dao.FileDao;
import com.ebsolutions.eventsadminservice.dal.util.FileLocationUtil;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.net.URL;

import static io.micronaut.http.HttpResponse.*;

@Slf4j
@Controller("/clients/{clientId}/files")
public class FileController {
    private final FileDao fileDao;

    public FileController(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Get(value = "/{filename}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> get(@NotBlank @PathVariable String clientId, @NotBlank @PathVariable String filename) {
        try {
            String fileLocation = FileLocationUtil.build(clientId, filename, "csv");

            URL url = fileDao.read(fileLocation);

            return url != null ? ok(url) : noContent();
        } catch (DataProcessingException dbe) {
            return serverError(dbe);
        }
    }
}