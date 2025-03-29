package com.ebsolutions.eventsadminservice.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  private static final List<String> ALLOWED_ORIGINS =
      List.of(
          "http://localhost:3000",
          "https://events.otterandcow.com",
          "64.98.122.48",
          "96.10.245.210"
      );

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .cors(cors -> cors
            .configurationSource(request -> this.corsConfiguration())
        );
    return httpSecurity.build();
  }

  @Bean
  CorsConfiguration corsConfiguration() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedOrigins(ALLOWED_ORIGINS);
    return configuration;
  }
}
