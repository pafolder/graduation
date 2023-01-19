package com.pafolder.graduation.controller.admin;

import com.pafolder.graduation.controller.AbstractController;
import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.repository.MenuRepository;
import com.pafolder.graduation.repository.RestaurantRepository;
import com.pafolder.graduation.to.MenuTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.pafolder.graduation.controller.admin.AdminMenuController.REST_URL;

@RestController
@AllArgsConstructor
@Tag(name = "5.2 admin-menu-controller")
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController extends AbstractController {
    public static final String REST_URL = "/api/admin/menus";
    public static final String NO_RESTAURANT_FOUND = "No restaurant found";
    public static final String MENU_ALREADY_EXISTS = "Menu already exists";
    public static final String INCORRECT_MENU_DATE = "Menu date is wrong: should be tomorrow or later";

    private MenuRepository menuRepository;
    private RestaurantRepository restaurantRepository;

    @GetMapping
    @Operation(summary = "Get menu list for tomorrow's voting", security = {@SecurityRequirement(name = "basicScheme")})
    public List<Menu> getAllMenusForTomorrow() {
        log.info("getAllMenusForTomorrow()");
        return menuRepository.findAllByDate(LocalDate.now().plusDays(1));
    }

    @PostMapping
    @CacheEvict(cacheNames = {"menus"}, allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new menu", security = {@SecurityRequirement(name = "basicScheme")})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "No date defaults to tomorrow")
    @Transactional
    public ResponseEntity<Menu> createMenu(@Valid @RequestBody MenuTo menuTo) {
        log.info("createMenu()");
        LocalDate date = menuTo.getDate() == null ? LocalDate.now().plusDays(1) : menuTo.getDate();
        if (!date.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, INCORRECT_MENU_DATE);
        }
        Restaurant restaurant = menuTo.getRestaurantId() != null ?
                restaurantRepository.findById(menuTo.getRestaurantId()).orElse(null) : null;
        if (restaurant == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_RESTAURANT_FOUND);
        }
        Menu menu = new Menu(null, date, restaurant, menuTo.getMenuItems());
        if (menuRepository.findByDateAndRestaurantId(date, restaurant.getId()).isEmpty()) {
            Menu createdMenu = menuRepository.save(menu);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}").buildAndExpand(createdMenu.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(createdMenu);
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, MENU_ALREADY_EXISTS);
        }
    }

    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = {"menus"}, allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete next date's menu", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "Menu Id to delete")
    @Transactional
    public void deleteNextDatesMenu(@PathVariable int id) {
        log.info("deleteNextDatesMenu()");
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isEmpty() || !(menu.get().getMenuDate().isAfter(LocalDate.now()))) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, INCORRECT_MENU_DATE);
        }
        menuRepository.deleteById(id);
    }
}
