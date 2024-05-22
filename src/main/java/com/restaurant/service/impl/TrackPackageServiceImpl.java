package com.restaurant.service.impl;

import com.restaurant.rest.TrackingPackageRestClient;
import com.restaurant.rest.impl.TrackingPackageRestClientImpl;
import com.restaurant.service.TrackPackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TrackPackageServiceImpl implements TrackPackageService {

  private final TrackingPackageRestClient trackingPackageRestClient;

  public TrackPackageServiceImpl(TrackingPackageRestClient trackingPackageRestClient) {
    this.trackingPackageRestClient = trackingPackageRestClient;
  }

  @Override
  public ResponseEntity<String> getTrackPackage(String trackingNumber) {
    // track package
    var trackPackage = trackingPackageRestClient.getTrackPackage(trackingNumber);

    // wait for the response
    try {
      var trackPackageResponse = trackPackage.get();
        return ResponseEntity.ok(trackPackageResponse.getBody());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Invalid tracking number");
    }
  }

}
