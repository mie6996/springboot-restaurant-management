package com.restaurant.service.impl;

import com.restaurant.entity.Menu;
import com.restaurant.exception.NoContentException;
import com.restaurant.exception.RepeatDataException;
import com.restaurant.mapper.ObjectMapper;
import com.restaurant.repository.MenuRepository;
import com.restaurant.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.restaurant.util.Utils.removeSpacesAndLowerCase;

/**
 * This class provides an implementation of the MenuService interface.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository repository;

    /**
     * Retrieves a paginated list of Menu objects based on the given parameters.
     *
     * @param limit the maximum number of items to return in the page
     * @param offset the number of items to skip before beginning to return results
     * @param isActive specifies whether only active or inactive menus should be returned
     * @param keyword the keyword to use when searching for menus
     * @return a Page object containing the requested Menu objects
     */
    @Override
    public Page<Menu> getAll(Integer limit, Integer offset, Boolean isActive, String keyword) {

        if (limit < 0 || offset < 0 || offset > 1000) {
            throw new IllegalArgumentException("Page limit and offset must be positive");
        }

        keyword = removeSpacesAndLowerCase(keyword);

        PageRequest pageRequest = PageRequest.of(offset, limit);

        return repository.findAllByIsActiveAndSearch(pageRequest, isActive, keyword);

    }

    /**
     * Creates a new Menu object in the database.
     *
     * @param menuDto the Menu object to create
     * @return the created Menu object
     * @throws RepeatDataException if a menu with the same name already exists
     */
    @Override
    public Menu create(Menu menuDto) {
        List<Menu> foundMenusByName =
                repository.findMenuByNameAndIsActive(removeSpacesAndLowerCase(menuDto.getName()), true);

        // Check if the menu already exists by checking if the name exists
        if (foundMenusByName.size() >= 1) {
            throw new RepeatDataException("The menu already exists!");
        }

        Menu menuEntity = ObjectMapper.mapDtoToEntity(menuDto);

        return repository.save(menuEntity);

    }

    /**
     * Updates an existing Menu object in the database.
     *
     * @param id the ID of the Menu object to update
     * @param menuDto the updated Menu object
     * @return the updated Menu object
     * @throws NoContentException if the specified menu does not exist
     * @throws RepeatDataException if a menu with the same name already exists
     */
    @Override
    public Menu update(Long id, Menu menuDto) {
        // Check menu has id is exists
        Menu foundMenu = repository.findByIdAndIsActive(id, true)
                .orElseThrow(() -> new NoContentException("This menu does not exist!"));

        // Found menu by name
        List<Menu> foundMenusByName =
                repository.findMenuByNameAndIsActive(removeSpacesAndLowerCase(menuDto.getName()), true);
        // If the menu has name already exists and not is found menu throw RepeatDataException
        if (foundMenusByName.size() == 1 && !foundMenusByName.get(0).equals(foundMenu)) {
            throw new RepeatDataException("The menu already exists!");
        }

        // Update foundMenu
        Menu menuEntity = ObjectMapper.toEntityUpdate(menuDto, foundMenu);

        return repository.save(menuEntity);

    }

    /**
     * Soft-deletes an existing Menu object in the database.
     *
     * @param id the ID of the Menu object to delete
     * @throws NoContentException if the specified menu does not exist
     */
    @Override
    public void delete(Long id) {

        repository.findByIdAndIsActive(id, true)
                .orElseThrow(() -> new NoContentException("This menu does not exist!"));

        repository.softDeleteById(id);

    }

    /**
     * Retrieves a Menu object from the database based on the given ID.
     *
     * @param id the ID of the Menu object to retrieve
     * @return the Menu object with the specified ID
     * @throws NoContentException if the specified menu does not exist
     */
    @Override
    public Menu findById(Long id) {

        Optional<Menu> optional = repository.findByIdAndIsActive(id, true);
        // Check menu is exists
        if (optional.isEmpty()) {
            throw new NoContentException("The menu has id = " + id + " does not exist!");
        }

        return optional.get();

    }

}
