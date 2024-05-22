package com.restaurant.service.impl;

import com.restaurant.config.JwtService;
import com.restaurant.dto.AuthenticationResponseDto;
import com.restaurant.dto.LoginRequestDto;
import com.restaurant.dto.RegisterRequestDto;
import com.restaurant.entity.Role;
import com.restaurant.entity.User;
import com.restaurant.repository.UserRepository;
import com.restaurant.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public AuthenticationResponseDto register(RegisterRequestDto requestDto) {
    User user = User.builder()
            .firstname(requestDto.getFirstname())
            .lastname(requestDto.getLastname())
            .email(requestDto.getEmail())
            .password(passwordEncoder.encode(requestDto.getPassword()))
            .role(Role.USER)
            .build();

    repository.save(user);
    String accessToken = jwtService.generateAccessToken(user);
    String refreshToken = jwtService.generateRefreshToken();

    return AuthenticationResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }

  @Override
  public AuthenticationResponseDto login(LoginRequestDto requestDto) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        requestDto.getEmail(),
        requestDto.getPassword()
      )
    );

    User user = repository.findByEmail(requestDto.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found!"));

    String accessToken = jwtService.generateAccessToken(user);
    String refreshToken = jwtService.generateRefreshToken();

    return AuthenticationResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }

}
