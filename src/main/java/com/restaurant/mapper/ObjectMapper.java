package com.restaurant.mapper;

import com.restaurant.entity.Menu;

import static com.restaurant.util.Utils.removeSpacesAndLowerCase;

public class ObjectMapper {
    public static Menu mapDtoToEntity(Menu dto) {
        dto.setName(removeSpacesAndLowerCase(dto.getName()).toLowerCase());
        dto.setDescription(removeSpacesAndLowerCase(dto.getDescription()));
        dto.setImage(removeSpacesAndLowerCase(dto.getImage()));
        dto.setAdditionalDetails(removeSpacesAndLowerCase(dto.getAdditionalDetails()));
        dto.setIsActive(true);

        return dto;
    }

    public static Menu toEntityUpdate(Menu dto, Menu oldMenu) {
        oldMenu.setName(removeSpacesAndLowerCase(dto.getName()).toLowerCase());
        oldMenu.setDescription(removeSpacesAndLowerCase(dto.getDescription()));
        oldMenu.setImage(removeSpacesAndLowerCase(dto.getImage()));
        oldMenu.setAdditionalDetails(removeSpacesAndLowerCase(dto.getAdditionalDetails()));

        return oldMenu;
    }

}
