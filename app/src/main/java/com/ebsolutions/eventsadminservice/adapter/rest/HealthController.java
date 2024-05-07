package com.ebsolutions.eventsadminservice.adapter.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HealthController {
    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok("Service is alive and the time is " + LocalDateTime.now());
    }
}