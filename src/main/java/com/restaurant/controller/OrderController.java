package com.restaurant.controller;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.OrderItemRequestDto;
import com.restaurant.dto.OrderRequestDto;
import com.restaurant.service.OrderItemService;
import com.restaurant.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.restaurant.constant.Constants.LIMIT_DEFAULT;
import static com.restaurant.constant.Constants.OFFSET_DEFAULT;

/**
 * Bill controller
 */
@RestController
@RequestMapping("/api/v1/orders/")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderItemService orderItemService;

  /**
   * Get all orders
   *
   * @param limit  max number of orders to return
   * @param offset offset to start returning orders from
   * @return response containing list of orders
   */
  @GetMapping("")
  public ResponseEntity<ApiResponse> getAll(@RequestParam(defaultValue = LIMIT_DEFAULT) Integer limit,
                                            @RequestParam(defaultValue = OFFSET_DEFAULT) Integer offset) {

    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.builder()
                    .success(true)
                    .message("Get orders successfully!")
                    .data(orderService.getAll(limit, offset))
                    .build());

  }

  /**
   * Create a new order
   *
   * @param order the new order to create
   * @return response containing the created order
   */
  @PostMapping("")
  public ResponseEntity<ApiResponse> create(@Valid @RequestBody OrderRequestDto order) {

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.builder()
                    .success(true)
                    .data(orderService.create(order))
                    .message("Create order successfully!")
                    .build());

  }

  /**
   * Find an order by id
   *
   * @param id the id of the order to find
   * @return response containing the found order
   */
  @GetMapping("{id}")
  public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {

    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.builder()
                    .success(true)
                    .data(orderService.findById(id))
                    .message("Get order has id = " + id + " successfully!")
                    .build());

  }

  /**
   * Delete an order by id
   *
   * @param id the id of the order to delete
   * @return response indicating success or failure
   */

  @DeleteMapping("{id}")
  public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {

    orderService.delete(id);
    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.builder()
                    .success(true)
                    .message("Delete order has id = " + id + " successfully!")
                    .build());

  }

  /**
   * Get an order item by id
   *
   * @param itemId the id of the order item to find
   * @return response containing the found order item
   */

  @GetMapping("{orderId}/order-items")
  public ResponseEntity<ApiResponse> getOrderItemById(@RequestParam(name = "id") Long itemId) {

    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.builder()
                    .success(true)
                    .data(orderItemService.findById(itemId))
                    .message("Get order item has id = " + itemId + " successfully!")
                    .build());

  }

  /**
   * Add an order item to an order
   *
   * @param orderId the id of the order to add the item to
   * @param dto     the order item to add
   * @return response containing the created order item
   */
  @PostMapping("{orderId}/order-items")
  public ResponseEntity<ApiResponse> addOrderItem(@PathVariable Long orderId, @Valid @RequestBody OrderItemRequestDto dto) {

    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.builder()
                    .success(true)
                    .data(orderItemService.create(orderId, dto))
                    .message("Add new order item to order has id = " + orderId + " successfully!")
                    .build());
  }

  /**
   * Update an order item in an order
   *
   * @param orderId the id of the order containing the item to update
   * @param itemId  the id of the item to update
   * @param dto     the new values for the order item
   * @return response containing the updated order item
   */
  @PutMapping("{orderId}/order-items")
  public ResponseEntity<ApiResponse> updateOrderItem(@PathVariable Long orderId, @RequestParam(name = "id") Long itemId,
                                                     @Valid @RequestBody OrderItemRequestDto dto) {

    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.builder()
                    .success(true)
                    .data(orderItemService.update(orderId, itemId, dto))
                    .message("Update order has id = " + orderId + " successfully!")
                    .build());

  }

  /**
   * Remove an order item from an order
   *
   * @param orderId the id of the order to remove the item from
   * @param itemId  the id of the item to remove
   * @return response indicating success or failure
   */
  @DeleteMapping("{orderId}/order-items")
  public ResponseEntity<ApiResponse> removeOrderItem(@PathVariable Long orderId, @RequestParam(name = "id") Long itemId) {

    orderItemService.delete(orderId, itemId);
    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.builder()
                    .success(true)
                    .message("Delete order item has id = " + itemId + " successfully!")
                    .build());

  }

}
