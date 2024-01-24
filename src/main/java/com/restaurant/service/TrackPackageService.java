package com.restaurant.service;

import reactor.core.publisher.Mono;

public interface TrackPackageService {
  Mono<String> getTrackPackage(String trackingNumber);

}
