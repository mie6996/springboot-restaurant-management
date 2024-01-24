package com.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse implements Serializable {
  Boolean success;
  String message;
  Object data;
}
