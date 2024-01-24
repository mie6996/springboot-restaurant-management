package com.restaurant.service;

import com.restaurant.dto.OrderItemRequestDto;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Order;
import com.restaurant.entity.OrderItem;
import com.restaurant.exception.NoContentException;
import com.restaurant.repository.MenuRepository;
import com.restaurant.repository.OrderItemRepository;
import com.restaurant.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * This class provides an implementation of the OrderItemService interface.
 */
@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
  private OrderRepository orderRepository;
  private OrderItemRepository orderItemRepository;
  private MenuRepository menuRepository;

  /**
   * Check order item is in order or not
   *
   * @param order     order
   * @param orderItem order item
   * @return true if order item is in order, otherwise
   */
  private boolean isOrderItemExistInOrder(Order order, OrderItem orderItem) {
    for (OrderItem item : order.getOrderItems()) {
      if (Objects.equals(item, orderItem)) {
        return true;
      }
    }
    return false;

  }

  /**
   * Creates a new OrderItem for the given Order.
   *
   * @param orderId the id of the Order to add the new OrderItem to
   * @param dto     the OrderItemRequestDto containing the menu id and quantity for the new OrderItem
   * @return the newly created OrderItem
   * @throws NoContentException if the given Order or Menu does not exist
   */
  @Override
  public OrderItem create(Long orderId, OrderItemRequestDto dto) {
    Long menuId = dto.getMenuId();
    Integer quantity = dto.getQuantity();

    // Check order exists
    Order foundOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoContentException("Order has id = " + orderId + " does not exist!"));

    // Check menu exists
    Menu foundMenu = menuRepository.findByIdAndIsActive(menuId, true)
            .orElseThrow(() -> new NoContentException("Menu has id = " + menuId + " does not exist!"));

    // Check menu exists in the order
    for (OrderItem orderItem : foundOrder.getOrderItems()) {
      if (orderItem.getMenu().getId().equals(menuId)) {
        orderItem.setQuantity(quantity);
        orderItem.setOrderTime(new Date());
        orderItem.calculateTotalPrice();

        foundOrder.calculateTotalPrice();
        return orderItemRepository.save(orderItem);
      }
    }

    OrderItem orderItem = OrderItem.builder()
            .menu(foundMenu)
            .order(foundOrder)
            .quantity(quantity)
            .totalPrice(foundMenu.getPrice() * quantity)
            .orderTime(new Date())
            .build();

    foundOrder.addOrderItem(orderItem);
    foundOrder.calculateTotalPrice();

    return orderItemRepository.save(orderItem);

  }

  /**
   * Updates the quantity of an existing OrderItem in the given Order.
   *
   * @param orderId     the id of the Order containing the OrderItem to update
   * @param orderItemId the id of the OrderItem to update
   * @param dto         the OrderItemRequestDto containing the updated quantity for the OrderItem
   * @return the updated OrderItem
   * @throws NoContentException if the given Order or OrderItem does not exist
   */
  @Override
  public OrderItem update(Long orderId, Long orderItemId, OrderItemRequestDto dto) {
    Integer quantity = dto.getQuantity();

    // Check order exists
    Order foundOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoContentException("Order has id = " + orderId + " does not exist!"));

    // Check menu exists
    OrderItem foundOrderItem = orderItemRepository.findById(orderItemId)
            .orElseThrow(() -> new NoContentException("Order item has id = " + orderItemId + " does not exist!"));

    if (!isOrderItemExistInOrder(foundOrder, foundOrderItem)) {
      throw new NoContentException("Order item has id = " + orderItemId + " not in order!");
    }

    foundOrderItem.setQuantity(quantity);
    foundOrderItem.calculateTotalPrice();

    List<OrderItem> orderItems = foundOrder.getOrderItems();

    int index = 0;
    for (OrderItem orderItem : orderItems) {
      if (Objects.equals(orderItem.getId(), orderItemId)) {
        orderItems.set(index, foundOrderItem);
        break;
      }
      index++;
    }

    foundOrder.calculateTotalPrice();

    return orderItemRepository.save(foundOrderItem);

  }

  /**
   * Deletes an existing OrderItem from the given Order.
   *
   * @param orderId     the id of the Order containing the OrderItem to delete
   * @param orderItemId the id of the OrderItem to delete
   * @throws NoContentException if the given Order or OrderItem does not exist
   */
  @Transactional
  @Override
  public void delete(Long orderId, Long orderItemId) {
    Order foundOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoContentException("Order has id = " + orderId + " does not exist!"));

    OrderItem foundOrderItem = orderItemRepository.findById(orderItemId)
            .orElseThrow(() -> new NoContentException("Order item has id = " + orderItemId + " does not exist!"));

    if (!isOrderItemExistInOrder(foundOrder, foundOrderItem)) {
      throw new NoContentException("Order item has id = " + orderItemId + " not in order!");
    }

    orderItemRepository.deleteById(orderItemId);

    List<OrderItem> orderItems = foundOrder.getOrderItems();
    orderItems.remove(foundOrderItem);
    if (orderItems.isEmpty()) {
      orderRepository.delete(foundOrder);
    } else {
      foundOrder.setOrderItems(orderItems);
      foundOrder.calculateTotalPrice();
      orderRepository.save(foundOrder);
    }

  }

  /**
   * Find an OrderItem entity by its id.
   *
   * @param id the id of the OrderItem to be found
   * @return the found OrderItem entity
   * @throws NoContentException if the OrderItem with the given id does not exist
   */
  @Override
  public OrderItem findById(Long id) {
    return orderItemRepository
            .findById(id)
            .orElseThrow(() -> new NoContentException("This order item does not exist!"));

  }

}
