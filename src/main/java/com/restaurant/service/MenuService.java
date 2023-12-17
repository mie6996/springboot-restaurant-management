package com.restaurant.service;

import com.restaurant.entity.Menu;
import org.springframework.data.domain.Page;

/**
 * Menu service
 */
public interface MenuService {

    Menu create(Menu dto);

    Page<Menu> getAll(Integer limit, Integer offset, Boolean isActive, String keyword);

    Menu update(Long id, Menu dto);

    void delete(Long id);

    Menu findById(Long id);

}
