package com.restaurant.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Component
public class TuanMotorbikeRestClient {

    private final WebClient webClientTuanMotorbike;

    public TuanMotorbikeRestClient(WebClient webClient) {
        this.webClientTuanMotorbike = webClient;
    }

    @Async("asyncExecutor")
    public CompletableFuture<? extends ResponseEntity<?>> getTuanMotorbike() {
        return webClientTuanMotorbike.get()
                .uri("https://tuanmotorbike.com/api/motorbikes")
                .retrieve()
                .toEntity(String.class)
                .toFuture();

    }
}
