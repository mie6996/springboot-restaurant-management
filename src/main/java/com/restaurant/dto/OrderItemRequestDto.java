package com.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto {
  Long id;
  Long menuId;

  @NotNull(message = "Quantity is required!")
  @Positive(message = "Quantity must be greater than 0!")
  Integer quantity;

}
