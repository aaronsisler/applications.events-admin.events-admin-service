package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.Client;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

@Slf4j
@Prototype
public class ClientDao {
    public Client read(String clientId) throws DataProcessingException {
        try {
            return null;
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        }
    }
}
