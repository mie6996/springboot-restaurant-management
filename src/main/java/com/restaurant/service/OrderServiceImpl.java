package com.restaurant.service;

import com.restaurant.dto.OrderItemRequestDto;
import com.restaurant.dto.OrderRequestDto;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Order;
import com.restaurant.entity.OrderItem;
import com.restaurant.exception.NoContentException;
import com.restaurant.repository.MenuRepository;
import com.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class provides an implementation of the OrderService interface.
 */
@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private OrderRepository orderRepository;

  /**
   * Retrieves a paginated list of Order objects based on the given parameters.
   *
   * @param limit  the maximum number of items to return in the page
   * @param offset the number of items to skip before beginning to return results
   * @return a Page object containing the requested Order objects
   */
  @Override
  public Page<Order> getAll(Integer limit, Integer offset) {

    if (limit < 0 || offset < 0 || offset > 1000) {
      throw new IllegalArgumentException("Page limit and offset must be positive");
    }

    PageRequest pageRequest = PageRequest.of(offset, limit);

    return orderRepository.findAll(pageRequest);

  }

  /**
   * Creates a new order object in the database.
   *
   * @param order the Order object to create
   * @return the created Order object
   * @throws NoContentException if the specified order does not exist
   */
  @Override
  public Order create(OrderRequestDto order) {

    List<OrderItemRequestDto> orderItemRequestDtos = order.getOrderItems();
    List<OrderItemRequestDto> updateOrderItemRequestDtos = new ArrayList<>();
    List<OrderItem> orderItems = new ArrayList<>();

    // Check for duplicate menu in orderItem
    orderItemRequestDtos.forEach(orderItemRequestDto -> {

      AtomicBoolean isEqual = new AtomicBoolean(true);
      // Check for the duplicate menu in orderItem
      updateOrderItemRequestDtos.forEach(updateOrderItemRequestDto -> {
        // If the duplicate menu
        if (Objects.equals(updateOrderItemRequestDto.getMenuId(), orderItemRequestDto.getMenuId())) {
          isEqual.set(false);
          // Set quantity again
          updateOrderItemRequestDto.setQuantity(updateOrderItemRequestDto.getQuantity()
                  + orderItemRequestDto.getQuantity());
        }
      });
      // If not duplicate
      if (isEqual.get()) {
        updateOrderItemRequestDtos.add(orderItemRequestDto);
      }

    });

    updateOrderItemRequestDtos.forEach(orderItemRequestDto -> {
      Optional<Menu> optionalMenu = menuRepository.findById(orderItemRequestDto.getMenuId());

      // Check menu is exists
      if (optionalMenu.isEmpty()) {
        throw new NoContentException("The menu does not exist!");
      }

      Menu foundMenu = optionalMenu.get();
      OrderItem newOrderItem = OrderItem.builder()
              .quantity(orderItemRequestDto.getQuantity())
              .orderTime(new Date())
              .menu(foundMenu)
              .build();

      newOrderItem.calculateTotalPrice();

      foundMenu.setOrderItems(Collections.singletonList(newOrderItem));
      orderItems.add(newOrderItem);

    });

    Order newOrder = Order.builder().orderItems(orderItems).build();
    newOrder.calculateTotalPrice();

    orderItems.forEach(orderItem -> orderItem.setOrder(newOrder));

    return orderRepository.save(newOrder);

  }

  /**
   * Delete an order item in the database
   *
   * @param id the ID of the Order object to delete
   * @throws NoContentException if the specified order does not exist
   */
  @Override
  public void delete(Long id) {

    // Check order has id is exists
    Order foundOrder = orderRepository.findById(id)
            .orElseThrow(() -> new NoContentException("The order is not found!"));

    orderRepository.delete(foundOrder);

  }

  /**
   * Retrieves an Order object from the database based on the given ID.
   *
   * @param id the ID of the Order object to retrieve
   * @return the Order object with the specified ID
   * @throws NoContentException if the specified order does not exist
   */
  @Override
  public Order findById(Long id) {
    // Check order has id is exists
    return orderRepository.findById(id)
            .orElseThrow(() -> new NoContentException("The order is not found!"));

  }

}
