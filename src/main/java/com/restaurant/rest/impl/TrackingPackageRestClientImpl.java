package com.restaurant.rest.impl;

import com.micro.healthcheck.api.DhpHealthIndicator;
import com.micro.healthcheck.api.HealthStatus;
import com.restaurant.rest.TrackingPackageRestClient;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

@Component
public class TrackingPackageRestClientImpl implements TrackingPackageRestClient, DhpHealthIndicator {
    private final WebClient webClientTrackingPackage;
    private final String trackingPackageUrl = "https://spx.vn/api/v2/fleet_order/tracking/search";

    public TrackingPackageRestClientImpl(WebClient webClient) {
        this.webClientTrackingPackage = webClient;
    }

    public String trackingNumberFunc(String resi) {
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
    public CompletableFuture<? extends ResponseEntity<String>> getTrackPackage(String trackingNumber) {
        // "https://spx.vn/api/v2/fleet_order/tracking/search?sls_tracking_number={trackingNumber}",
        return webClientTrackingPackage.get()
                .uri(trackingPackageUrl + "?sls_tracking_number=" + trackingNumberFunc(trackingNumber))
                .retrieve()
                .toEntity(String.class)
                .toFuture();
    }

    @Override
    public HealthStatus checkHealth() {
        var healthy = true;
        Exception exception = null;
        try {
            getTrackPackage("SPXVN048106749441").get();
        } catch (Exception e) {
            exception = e;
            healthy = false;
        }

        return HealthStatus.builder()
                .healthy(healthy)
                .urlBase(trackingPackageUrl)
                .exception(exception)
                .build();
    }

}
