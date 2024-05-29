package com.restaurant.rest;

import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

public interface TuanMotorbikeRestClient {
  CompletableFuture<? extends ResponseEntity<String>> getTuanMotorbike();
}