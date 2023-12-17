package com.restaurant.controller;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.LoginRequestDto;
import com.restaurant.dto.RegisterRequestDto;
import com.restaurant.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                .success(true)
                .data(service.register(requestDto))
                .message("Login successfully!")
                .build());

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.login(requestDto));
    }

}
