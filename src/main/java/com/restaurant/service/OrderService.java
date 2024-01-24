package com.restaurant.service;

import com.restaurant.dto.OrderRequestDto;
import com.restaurant.entity.Order;
import org.springframework.data.domain.Page;

/**
 * Order service
 */
public interface OrderService {
  Order create(OrderRequestDto dto);
  Page<Order> getAll(Integer limit, Integer offset);
  void delete(Long id);
  Order findById(Long id);

}
