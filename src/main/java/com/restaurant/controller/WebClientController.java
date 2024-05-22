package com.restaurant.controller;

import com.restaurant.service.TrackPackageService;
import com.restaurant.service.TuanMotorbikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/web-client")
public class WebClientController {

    private final TrackPackageService trackPackageService;
    private final TuanMotorbikeService tuanMotorbikeService;

    @Autowired
    public WebClientController(TrackPackageService trackPackageService, TuanMotorbikeService tuanMotorbikeService) {
        this.trackPackageService = trackPackageService;
        this.tuanMotorbikeService = tuanMotorbikeService;
    }

    @GetMapping("/track-package")
    public ResponseEntity<String> getTrackPackage(@RequestParam String trackingNumber) {
        log.info("Get track package");
        long startTime = System.currentTimeMillis();
        var response = trackPackageService.getTrackPackage(trackingNumber);
        long endTime = System.currentTimeMillis();
        log.info("Time taken: " + (endTime - startTime) + "ms");
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/motorbikes")
    public ResponseEntity<String> getMotorbikes() {
        log.info("Get track package");
        long startTime = System.currentTimeMillis();
        var response = tuanMotorbikeService.getMotorbikes();
        long endTime = System.currentTimeMillis();
        log.info("Time taken: " + (endTime - startTime) + "ms");
        return ResponseEntity.ok(response.getBody());
    }

}
