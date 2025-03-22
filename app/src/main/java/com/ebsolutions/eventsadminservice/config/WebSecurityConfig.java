package com.ebsolutions.eventsadminservice.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors
            .configurationSource(request -> this.createCorsConfiguration())
        )
        .authorizeHttpRequests(authorization ->
            authorization
                .requestMatchers("/actuator/health")
                .permitAll()
                .requestMatchers("/actuator/info")
                .permitAll()
                .anyRequest().authenticated()
        )
        .httpBasic(withDefaults());
    return http.build();
  }

  public CorsConfiguration createCorsConfiguration() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    configuration.setAllowCredentials(true);

    configuration.setAllowedOrigins(
        Collections.singletonList("http://localhost:3000")); // Replace with your origin

    return configuration;
  }
}
