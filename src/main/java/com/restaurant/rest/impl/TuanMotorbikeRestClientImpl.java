package com.restaurant.rest.impl;

import com.micro.healthcheck.api.DhpHealthIndicator;
import com.micro.healthcheck.api.HealthStatus;
import com.restaurant.rest.TuanMotorbikeRestClient;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Component
public class TuanMotorbikeRestClientImpl implements TuanMotorbikeRestClient, DhpHealthIndicator {
  private final WebClient webClientTuanMotorbike;
  private final String tuanMotorbikeUrl = "https://tuanmotorbike.com/api/motorbikes";

  public TuanMotorbikeRestClientImpl(WebClient webClient) {
    this.webClientTuanMotorbike = webClient;
  }

  @Async("asyncExecutor")
  public CompletableFuture<? extends ResponseEntity<String>> getTuanMotorbike() {
    return webClientTuanMotorbike.get()
        .uri(URI.create(tuanMotorbikeUrl))
        .retrieve()
        .toEntity(String.class)
        .toFuture();

  }

  @Override
  public HealthStatus checkHealth() {
    var healthy = true;
    Exception exception = null;

    try {
      getTuanMotorbike().get();
    } catch (Exception e) {
      healthy = false;
      exception = e;
    }

    return HealthStatus.builder()
        .healthy(healthy)
        .exception(exception)
        .urlBase(tuanMotorbikeUrl)
        .build();
  }

}
