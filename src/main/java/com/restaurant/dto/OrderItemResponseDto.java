package com.restaurant.dto;

import com.restaurant.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {
  Long id;
  Menu menu;
  Date orderTime;
  Integer quantity;
  Double totalPrice;

}
