package com.restaurant.repository;

import com.restaurant.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Menu repository
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
