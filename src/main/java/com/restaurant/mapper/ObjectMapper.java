package com.restaurant.mapper;

import com.restaurant.entity.Menu;

import static com.restaurant.util.Util.removeSpaces;

public class ObjectMapper {
  public static Menu mapDtoToEntity(Menu dto) {
    dto.setName(removeSpaces(dto.getName()));
    dto.setDescription(removeSpaces(dto.getDescription()));
    dto.setImage(removeSpaces(dto.getImage()));
    dto.setAdditionalDetails(removeSpaces(dto.getAdditionalDetails()));
    dto.setIsActive(true);

    return dto;
  }

  public static Menu toEntityUpdate(Menu dto, Menu oldMenu) {
    oldMenu.setName(removeSpaces(dto.getName()));
    oldMenu.setPrice(dto.getPrice());
    oldMenu.setDescription(removeSpaces(dto.getDescription()));
    oldMenu.setImage(removeSpaces(dto.getImage()));
    oldMenu.setAdditionalDetails(removeSpaces(dto.getAdditionalDetails()));

    return oldMenu;
  }

}
