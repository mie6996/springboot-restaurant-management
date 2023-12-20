package com.restaurant.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

  @NotNull(message = "Order items is required!")
  @NotEmpty(message = "Order items is required!")
  List<@Valid OrderItemRequestDto> orderItems;

}
