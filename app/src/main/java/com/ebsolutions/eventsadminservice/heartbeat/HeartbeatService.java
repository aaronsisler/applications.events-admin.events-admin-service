package com.ebsolutions.eventsadminservice.heartbeat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@EnableAsync
@Component
@Slf4j
public class HeartbeatService {
  @Scheduled(fixedRate = 1500, initialDelay = 1000)
  public void heartbeat() {
    log.info("Heartbeat goes 'Bump bump'");
  }
}
