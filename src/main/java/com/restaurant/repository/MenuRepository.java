package com.restaurant.repository;

import com.restaurant.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Menu repository
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findMenuByNameAndIsActive(String name, boolean isActive);

    @Query("SELECT m FROM Menu m WHERE m.isActive = :isActive AND (LOWER(m.name) LIKE %:keyword% " +
            "OR LOWER(m.description) LIKE %:keyword% " +
            "OR LOWER(m.additionalDetails) LIKE %:keyword%)")
    Page<Menu> findAllByIsActiveAndSearch(PageRequest pageRequest, boolean isActive, String keyword);

    Optional<Menu> findByIdAndIsActive(Long id, boolean isActive);

    @Transactional
    @Modifying
    @Query("UPDATE Menu m SET m.isActive = false WHERE m.id = :id")
    void softDeleteById(@Param("id") Long id);

}
