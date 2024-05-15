package com.restaurant.service;

import com.restaurant.rest.TrackingPackageRestClient;
import com.restaurant.rest.TuanMotorbikeRestClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class TrackPackageServiceImpl implements TrackPackageService {

  private final TrackingPackageRestClient trackingPackageRestClient;
  private final TuanMotorbikeRestClient tuanMotorbikeRestClient;

  public TrackPackageServiceImpl(TrackingPackageRestClient trackingPackageRestClient, TuanMotorbikeRestClient tuanMotorbikeRestClient) {
    this.trackingPackageRestClient = trackingPackageRestClient;
    this.tuanMotorbikeRestClient = tuanMotorbikeRestClient;
  }

  @Override
  public ResponseEntity<String> getTrackPackage(String trackingNumber) throws ExecutionException, InterruptedException {
    // get all motorbikes and track package
    var motorbikes = tuanMotorbikeRestClient.getTuanMotorbike();
    var trackPackage = trackingPackageRestClient.getTrackPackage(trackingNumber);

    CompletableFuture.allOf(motorbikes, trackPackage).join();

//    System.out.println("Motorbikes: " + motorbikes.get().getBody());
//    System.out.println("Track package: " + trackPackage.get().getBody());

    // return the response
    return ResponseEntity.ok("Get all motorbikes and track package");

  }




}
