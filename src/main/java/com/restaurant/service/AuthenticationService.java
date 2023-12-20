package com.restaurant.service;

import com.restaurant.dto.AuthenticationResponseDto;
import com.restaurant.dto.LoginRequestDto;
import com.restaurant.dto.RegisterRequestDto;

public interface AuthenticationService {

  AuthenticationResponseDto register(RegisterRequestDto requestDto);

  AuthenticationResponseDto login(LoginRequestDto requestDto);

}
