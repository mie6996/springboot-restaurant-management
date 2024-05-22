package com.restaurant.service;

import org.springframework.http.ResponseEntity;

public interface TrackPackageService {
  ResponseEntity<String> getTrackPackage(String trackingNumber);
}
