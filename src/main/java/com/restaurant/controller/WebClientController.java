package com.restaurant.controller;

import com.restaurant.service.TrackPackageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/web-client")
public class WebClientController {

  private TrackPackageService trackPackageService;

  @GetMapping("/track-package")
  public Mono<String> getTrackPackage(@RequestParam String trackingNumber) {
    log.info("Get track package");
    return trackPackageService.getTrackPackage(trackingNumber);
  }

}
