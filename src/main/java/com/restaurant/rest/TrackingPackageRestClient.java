package com.restaurant.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

@Component
public class TrackingPackageRestClient {

    private final WebClient webClientTrackingPackage;

    public TrackingPackageRestClient(WebClient webClient) {
        this.webClientTrackingPackage = webClient;
    }

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

    @Async("asyncExecutor")
    public CompletableFuture<? extends ResponseEntity<?>> getTrackPackage(String trackingNumber) {
        return webClientTrackingPackage.get()
                .uri("https://spx.vn/api/v2/fleet_order/tracking/search?sls_tracking_number={trackingNumber}",
                        trackingNumberFunc(trackingNumber))
                .retrieve()
                .toEntity(String.class)
                .toFuture();
    }
}
