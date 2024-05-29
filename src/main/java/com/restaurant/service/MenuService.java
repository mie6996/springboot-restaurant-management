package com.restaurant.service;

import com.restaurant.entity.Menu;

import java.util.List;

/**
 * Menu service
 */
public interface MenuService {
  Menu create(Menu dto);

  List<Menu> getAll(Integer limit, Integer offset, Boolean isActive, String keyword);

  Menu update(Long id, Menu dto);

  void delete(Long id);

  Menu findById(Long id);

}
