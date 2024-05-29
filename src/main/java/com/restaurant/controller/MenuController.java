package com.restaurant.controller;

import com.restaurant.dto.ApiResponse;
import com.restaurant.entity.Menu;
import com.restaurant.service.MenuService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * Menu controller
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {

  private MenuService service;

  /**
   * Get all menus
   *
   * @param limit    max number of menus to return
   * @param offset   offset to start returning menus from
   * @param isActive filter for active menus if true, inactive if false, true if null
   * @param keyword  keyword to search menu names, descriptions, and additional details by
   * @return response containing list of menus
   */
  @GetMapping("")
  public ResponseEntity<ApiResponse> getAll(@RequestParam(required = false) String keyword,
                                            @RequestParam(defaultValue = LIMIT_DEFAULT) Integer limit,
                                            @RequestParam(defaultValue = OFFSET_DEFAULT) Integer offset,
                                            @RequestParam(defaultValue = "true") boolean isActive) {
    log.info("Get all menus");
    return ResponseEntity.ok(ApiResponse.builder()
        .data(service.getAll(limit, offset, isActive, keyword))
        .success(true)
        .message("Get menus successfully!")
        .build()
    );
  }

  /**
   * Create a new menu
   *
   * @param menu the new menu to create
   * @return response containing the created menu
   */
  @PostMapping("")
  public ResponseEntity<ApiResponse> create(@Valid @RequestBody Menu menu) {
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
        .data(service.create(menu))
        .success(true)
        .message("Create menu successfully!")
        .build());
  }

  /**
   * Update an existing menu
   *
   * @param id   the id of the menu to update
   * @param menu the updated menu object
   * @return response containing the updated menu
   */
  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody Menu menu) {

    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
        .data(service.update(id, menu))
        .success(true)
        .message("Update the menu successfully!")
        .build());

  }

  /**
   * Deletes a menu by its ID.
   *
   * @param id the ID of the menu to delete
   * @return a ResponseEntity with a success status and message in the body
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
        .success(true)
        .message("Delete menu successfully!")
        .build());
  }

  /**
   * Find a menu by id
   *
   * @param id the id of the menu to find
   * @return response containing the found menu
   */
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
    Menu menu = service.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
        .data(menu)
        .success(true)
        .message("Find menu has id = " + id + " successfully!")
        .build());
  }

}
