package com.ebsolutions.eventsadminservice.dal.util;

import com.csvreader.CsvWriter;
import com.ebsolutions.eventsadminservice.dal.dto.PublishedScheduledEventDto;
import com.ebsolutions.eventsadminservice.exception.CsvGenerationException;
import com.ebsolutions.eventsadminservice.util.MetricsStopWatch;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Prototype
public class CsvFileGenerator {
    public InputStream create(List<PublishedScheduledEventDto> publishedEventScheduleDtos) throws IOException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try (stream) {
            CsvWriter writer = new CsvWriter(stream, ',', StandardCharsets.ISO_8859_1);
            writer.setRecordDelimiter(';');
            // WRITE Your CSV Here
            //writer.write("a;b");
            writer.endRecord();
            writer.close();
            stream.close();

            return new ByteArrayInputStream(stream.toByteArray());
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new CsvGenerationException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
        }
    }
}
