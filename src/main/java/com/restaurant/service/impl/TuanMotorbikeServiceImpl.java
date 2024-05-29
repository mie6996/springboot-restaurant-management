package com.restaurant.service.impl;

import com.restaurant.rest.TuanMotorbikeRestClient;
import com.restaurant.service.TuanMotorbikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TuanMotorbikeServiceImpl implements TuanMotorbikeService {
  private final TuanMotorbikeRestClient tuanMotorbikeRestClient;

  public TuanMotorbikeServiceImpl(TuanMotorbikeRestClient tuanMotorbikeRestClient) {
    this.tuanMotorbikeRestClient = tuanMotorbikeRestClient;
  }

  @Override
  public ResponseEntity<String> getMotorbikes() {
    var motorbikes = tuanMotorbikeRestClient.getTuanMotorbike();
    try {
      var motorbikeResponse = motorbikes.get();
      return ResponseEntity.ok(motorbikeResponse.getBody());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Invalid motorbike");
    }
  }

}
