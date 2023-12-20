package com.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * ErrorResponse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

  Boolean success;
  String code;
  String message;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  Map<String, String> validationErrors;

  public ErrorResponse(String code, String message) {
    this.success = false;
    this.code = code;
    this.message = message;
  }

  public ErrorResponse(String code, String message, Map<String, String> validationErrors) {
    this.success = false;
    this.code = code;
    this.message = message;
    this.validationErrors = validationErrors;
  }

}