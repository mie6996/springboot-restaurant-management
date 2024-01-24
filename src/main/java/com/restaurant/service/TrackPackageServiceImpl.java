package com.restaurant.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class TrackPackageServiceImpl implements TrackPackageService {
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

  @Override
  public Mono<String> getTrackPackage(String trackingNumber) {
    return webClient.get()
            .uri("https://spx.vn/api/v2/fleet_order/tracking/search?sls_tracking_number={trackingNumber}",
                    trackingNumberFunc(trackingNumber))
            .retrieve().bodyToMono(String.class);
  }

}
