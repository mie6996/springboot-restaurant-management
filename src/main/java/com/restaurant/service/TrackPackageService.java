package com.restaurant.service;

import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;

public interface TrackPackageService {
  ResponseEntity<String> getTrackPackage(String trackingNumber) throws ExecutionException, InterruptedException;

}
