package com.restaurant.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/web-client")
public class WebClientController {

  private WebClient webClient;

  public static String trackingNumberFunc(String resi) {
      String k = "MGViZmZmZTYzZDJhNDgxY2Y1N2ZlN2Q1ZWJkYzlmZDY=";
      long r = System.currentTimeMillis() / 1000L;
      String rs = String.valueOf(r);
      String data = resi + rs + k;

      String hash;
      try {
          MessageDigest md = MessageDigest.getInstance("SHA-256");
          byte[] hashedBytes = md.digest(data.getBytes());

          // Convert byte array to hexadecimal representation
          StringBuilder sb = new StringBuilder();
          for (byte b : hashedBytes) {
              sb.append(String.format("%02x", b));
          }
          hash = sb.toString();
      } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException("Error creating SHA-256 hash", e);
      }

      return resi + "|" + rs + hash;
  }

  @GetMapping("/track-package")
  public Mono<String> getTrackPackage(@RequestParam String trackingNumber) {
    return webClient.get()
            .uri("https://spx.vn/api/v2/fleet_order/tracking/search?sls_tracking_number={trackingNumber}", trackingNumberFunc(trackingNumber))
            .retrieve().bodyToMono(String.class);
  }

}
