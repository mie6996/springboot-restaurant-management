package com.restaurant.controller;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class GetRequestTask<T> implements Runnable {
    private final URI url;
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    public GetRequestTask(URI url) {
        this.url = url;
    }

    @Override
    public void run() {
        ResponseEntity<String> result = this.restTemplate.getForEntity(url, String.class);
        System.out.println(result.getBody());
    }


}