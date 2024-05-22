package com.restaurant.rest;

import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

public interface TrackingPackageRestClient {
    CompletableFuture<? extends ResponseEntity<String>> getTrackPackage(String trackingNumber);

}
