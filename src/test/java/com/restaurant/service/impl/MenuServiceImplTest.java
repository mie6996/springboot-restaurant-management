package com.restaurant.service.impl;

import com.restaurant.entity.Menu;
import com.restaurant.exception.NoContentException;
import com.restaurant.exception.RepeatDataException;
import com.restaurant.repository.MenuRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.restaurant.util.Utils.removeSpacesAndLowerCase;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * MenuServiceImplTest
 */
@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {
    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    private Menu menuDto;

    @BeforeEach
    void beforeEach() {

        menuDto = new Menu();
        menuDto.setId(1L);
        menuDto.setName("burger");
        menuDto.setImage("burger.jpg");
        menuDto.setDescription("Juicy beef patty with lettuce and tomato");
        menuDto.setPrice(10.99);
        menuDto.setAdditionalDetails("Add cheese for $1.00");
        menuDto.setIsActive(true);

    }

    @Test
    @DisplayName("Get all menus - Called With Valid Parameters")
    void getAll_shouldReturnPageOfMenus_whenCalledWithValidParameters() {

        Integer offset = 0;
        Integer limit = 5;
        Boolean isActive = true;
        String keyword = "";
        List<Menu> menuList = new ArrayList<>();
        Page<Menu> page = new PageImpl<>(menuList);
        when(menuRepository.findAllByIsActiveAndSearch(any(PageRequest.class), eq(isActive), eq(keyword))).thenReturn(page);

        Page<Menu> result = menuService.getAll(limit, offset, isActive, keyword);

        verify(menuRepository, times(1)).findAllByIsActiveAndSearch(any(PageRequest.class), eq(isActive), eq(keyword));
        Assertions.assertEquals(page, result);
    }

    @Test
    @DisplayName("Get all menus - Called With Not Valid Parameters")
    void getAll_shouldThrowIllegalArgumentException_whenCalledWithNotValidParameters() {

        Integer offset = 0;
        Integer limit = -5;
        Boolean isActive = true;
        String keyword = "";

        Assertions.assertThrows(IllegalArgumentException.class, () -> menuService.getAll(limit, offset, isActive, keyword));

    }

    @Test
    @DisplayName("Create a new menu - Called With Valid Parameters")
    void create_shouldCreateNewMenu_whenCalledWithValidParameters() {

        when(menuRepository.findMenuByNameAndIsActive(eq(removeSpacesAndLowerCase(menuDto.getName())),
                eq(true))).thenReturn(new ArrayList<>());
        when(menuRepository.save(any(Menu.class))).thenReturn(menuDto);

        Menu result = menuService.create(menuDto);

        Assertions.assertEquals(menuDto, result);

        verify(menuRepository, times(1)).findMenuByNameAndIsActive(eq(menuDto.getName()), eq(true));
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    @DisplayName("Create menu - Menu Name Already Exists")
    void create_shouldThrowRepeatDataException_whenMenuNameAlreadyExists() {

        Menu existingMenu = new Menu();
        existingMenu.setName("Burger");

        when(menuRepository.findMenuByNameAndIsActive(eq(removeSpacesAndLowerCase(existingMenu.getName())), eq(true)))
                .thenReturn(Collections.singletonList(existingMenu));

        Assertions.assertThrows(RepeatDataException.class, () -> menuService.create(menuDto));

    }

    @Test
    @DisplayName("Update menu - Menu Does Not Exist")
    void update_shouldThrowNoContentException_whenMenuDoesNotExist() {

        Menu existingMenu = new Menu();
        existingMenu.setId(100L);
        existingMenu.setName("pho");
        existingMenu.setImage("burger.jpg");
        existingMenu.setDescription("Juicy beef patty with lettuce and tomato");
        existingMenu.setPrice(10.99);
        existingMenu.setAdditionalDetails("Add cheese for $1.00");
        existingMenu.setIsActive(true);

        Assertions.assertThrows(NoContentException.class,
                () -> menuService.update(existingMenu.getId(), menuDto));

    }

    @Test
    @DisplayName("Update menu - Called With Valid Parameters")
    void update_shouldUpdateMenu_whenCalledWithValidParameters() {

        Menu existingMenu = new Menu();
        existingMenu.setId(1L);
        existingMenu.setName("Burger Update");
        existingMenu.setImage("burger.jpg");
        existingMenu.setDescription("Juicy beef patty with lettuce and tomato");
        existingMenu.setPrice(10.99);
        existingMenu.setAdditionalDetails("Add cheese for $1.00");
        existingMenu.setIsActive(true);

        when(menuRepository.findByIdAndIsActive(eq(menuDto.getId()), eq(true)))
                .thenReturn(Optional.of(existingMenu));

        when(menuRepository.findMenuByNameAndIsActive(eq(removeSpacesAndLowerCase(menuDto.getName())), eq(true)))
                .thenReturn(new ArrayList<>());

        when(menuRepository.save(any(Menu.class))).thenReturn(menuDto);

        Assertions.assertEquals(removeSpacesAndLowerCase(menuDto.getName()),
                removeSpacesAndLowerCase(menuService.update(menuDto.getId(), menuDto).getName()));

    }

    @Test
    @DisplayName("Update menu - Menu Name Already Exists")
    void update_shouldThrowRepeatDataException_whenMenuNameAlreadyExists() {

        Menu menu1 = new Menu();
        menu1.setId(100L);
        menu1.setName("burger");

        Menu menu2 = new Menu();
        menu2.setId(200L);
        menu2.setName("burger");

        when(menuRepository.findByIdAndIsActive(eq(menu1.getId()), eq(true)))
                .thenReturn(Optional.of(menu1));

        when(menuRepository.findMenuByNameAndIsActive(eq(removeSpacesAndLowerCase(menuDto.getName())), eq(true)))
                .thenReturn(Collections.singletonList(menu2));

        Assertions.assertThrows(RepeatDataException.class, () -> menuService.update(menu1.getId(), menuDto));

    }

    @Test
    @DisplayName("Delete menu - Id of menu is not valid")
    void delete_shouldThrowNoContentException_WhenIdIsNotValid() {

        when(menuRepository.findByIdAndIsActive(eq(menuDto.getId()), eq(true)))
                .thenThrow(NoContentException.class);

        // Assert
        Assertions.assertThrows(NoContentException.class, () -> menuService.delete(menuDto.getId()));
        verify(menuRepository, times(1)).findByIdAndIsActive(eq(menuDto.getId()), eq(true));
        verify(menuRepository, times(0)).softDeleteById(eq(menuDto.getId()));

    }

    @Test
    @DisplayName("Delete menu - Id is valid")
    void delete_shouldDeleteMenu_WhenIdIsValid() {

        Menu existingMenu = new Menu();
        existingMenu.setId(1L);
        existingMenu.setIsActive(true);

        when(menuRepository.findByIdAndIsActive(eq(menuDto.getId()), eq(true)))
                .thenReturn(Optional.of(existingMenu));

        menuService.delete(menuDto.getId());

        // Assert
        verify(menuRepository, times(1)).findByIdAndIsActive(eq(menuDto.getId()), eq(true));
        verify(menuRepository, times(1)).softDeleteById(eq(menuDto.getId()));

    }

    @Test
    @DisplayName("Find menu by id - Menu with same name already exists")
    void findById() {

    }
}