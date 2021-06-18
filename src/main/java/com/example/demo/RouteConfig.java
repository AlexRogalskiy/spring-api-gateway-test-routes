package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

  @Value("${gateway.hostname:http://localhost:8081}")
  String uri;

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder
        .routes()
        .route(r -> r.path("/foo").filters(f -> f.prefixPath("/bar")).uri(uri))
        .build();
  }
}
