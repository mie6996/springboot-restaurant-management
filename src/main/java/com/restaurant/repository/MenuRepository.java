package com.restaurant.repository;

import com.restaurant.entity.Menu;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.lower;
import static org.jooq.impl.DSL.table;

/**
 * Menu repository
 */
@Repository
public class MenuRepository {
  DefaultDSLContext dsl;

  @Autowired
  public MenuRepository(DefaultDSLContext dsl) {
    this.dsl = dsl;
  }

  private static final String TABLE_NAME = "menus";

  public List<Menu> findMenuByNameAndIsActive(String name, boolean isActive) {
    List<Condition> conditions = new ArrayList<>();
    conditions.add(field("name").eq(name));
    conditions.add(field("is_active").eq(isActive));

    return dsl.select()
            .from(table(TABLE_NAME))
            .where(conditions)
            .fetch()
            .into(Menu.class);
  }

  public List<Menu> findAll(int offset, int limit, Boolean isActive, String keyword) {
    List<Condition> conditions = new ArrayList<>();

    if (isActive != null) {
      conditions.add(field("is_active").eq(isActive));
    }

    if (keyword != null) {
      Field<String> nameField = field("name", String.class);
      Field<String> descriptionField = field("description", String.class);
      Field<String> additionalDetailsField = field("additional_details", String.class);

      Condition combinedCondition = lower(nameField).likeIgnoreCase("%" + keyword + "%")
              .or(lower(descriptionField).likeIgnoreCase("%" + keyword + "%"))
              .or(lower(additionalDetailsField).likeIgnoreCase("%" + keyword + "%"));
      conditions.add(combinedCondition);
    }

    return dsl.select()
            .from(table(TABLE_NAME))
            .where(conditions)
            .limit(limit)
            .offset(offset)
            .fetch()
            .into(Menu.class);
  }

  public Optional<Menu> findByIdAndIsActive(Long id, boolean isActive) {
    List<Condition> conditions = new ArrayList<>();
    conditions.add(field("id").eq(id));
    conditions.add(field("is_active").eq(isActive));

    return dsl.select()
            .from(table(TABLE_NAME))
            .where(conditions)
            .fetchOptional()
            .map(record -> record.into(Menu.class));
  }

  public void softDeleteById(@Param("id") Long id) {
    dsl.update(table(TABLE_NAME))
            .set(field("is_active"), false)
            .where(field("id").eq(id))
            .execute();
  }

  public Menu save(Menu menu) {
    Long id = Objects.requireNonNull(dsl.insertInto(table(TABLE_NAME))
                    .set(field("name"), menu.getName())
                    .set(field("description"), menu.getDescription())
                    .set(field("additional_details"), menu.getAdditionalDetails())
                    .set(field("image"), menu.getImage())
                    .set(field("price"), menu.getPrice())
                    .returning(field("id", Long.class))
                    .fetchOne())
                    .into(Long.class);

    menu.setId(id);
    return menu;
  }

  public Optional<Menu> findById(Long id) {
    List<Condition> conditions = new ArrayList<>();
    conditions.add(field("id").eq(id));

    return dsl.select(table(TABLE_NAME))
            .where(conditions)
            .fetchOptional()
            .map(record -> record.into(Menu.class));
  }

  public Menu update(Menu menu) {
    dsl.update(table(TABLE_NAME))
            .set(field("name"), menu.getName())
            .set(field("description"), menu.getDescription())
            .set(field("additional_details"), menu.getAdditionalDetails())
            .set(field("image"), menu.getImage())
            .set(field("price"), menu.getPrice())
            .where(field("id").eq(menu.getId()))
            .execute();

    return menu;
  }
}
