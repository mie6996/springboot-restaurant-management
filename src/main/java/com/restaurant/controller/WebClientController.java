package com.restaurant.controller;

import com.restaurant.service.TrackPackageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/web-client")
public class WebClientController {

  private TrackPackageService trackPackageService;

  @GetMapping("/track-package")
  public ResponseEntity<String> getTrackPackage(@RequestParam String trackingNumber) throws ExecutionException, InterruptedException {
    log.info("Get track package");
    long startTime = System.currentTimeMillis();
    var response = trackPackageService.getTrackPackage(trackingNumber);
    long endTime = System.currentTimeMillis();
    log.info("Time taken: " + (endTime - startTime) + "ms");
    return ResponseEntity.ok(response.getBody());
  }

}
