package com.restaurant.service;

import com.restaurant.dto.OrderItemRequestDto;
import com.restaurant.entity.OrderItem;

/**
 * Order item service
 */
public interface OrderItemService {
  OrderItem findById(Long id);
  OrderItem create(Long orderId, OrderItemRequestDto dto);
  OrderItem update(Long orderId, Long orderItemId, OrderItemRequestDto dto);
  void delete(Long orderId, Long orderItemId);

}
