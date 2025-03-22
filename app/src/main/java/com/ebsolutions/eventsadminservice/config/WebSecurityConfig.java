package com.ebsolutions.eventsadminservice.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorization ->
                authorization
                    .requestMatchers("/actuator/health")
                    .permitAll()
//                .requestMatchers("/actuator/info")
//                .permitAll()
                    .anyRequest().authenticated()
        )
        .httpBasic(withDefaults());
    return http.build();
  }

}
