package com.ebsolutions.eventsadminservice.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.cors.CorsConfiguration;

//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig {
  private static final List<String> ALLOWED_ORIGINS =
      List.of(
          "http://localhost:3000",
          "https://events.otterandcow.com",
          // House or gym
          "64.98.122.48",
          // House or gym
          "96.10.245.210",
          // Tavern
          "107.12.84.23"
      );

  //  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//    httpSecurity
//        .cors(cors -> cors
//            .configurationSource(request -> this.corsConfiguration())
//        )
//        .authorizeHttpRequests(
//            auth ->
//                auth.
//                    requestMatchers("/establishments")
//                    .permitAll()
//                    .anyRequest()
//                    .permitAll());
//    return httpSecurity.build();
//  }

  //  @Bean
  CorsConfiguration corsConfiguration() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedOrigins(ALLOWED_ORIGINS);
    return configuration;
  }
}
