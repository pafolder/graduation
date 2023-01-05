package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.to.MenuTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static com.pafolder.graduation.controller.AbstractController.REST_URL;
import static com.pafolder.graduation.util.DateTimeUtil.getCurrentDate;
import static com.pafolder.graduation.util.DateTimeUtil.getNextVotingDate;

@RestController
@Tag(name = "5 Admin", description = "Administrator's API")
@RequestMapping(value = REST_URL + "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController extends AbstractController {
    public static String RESTAURANTID_SHOULD_BE_NULL = "restaurantId should be null for creating new restaurant";
    public static String NO_RESTAURANT_FOUND = "No restaurant found";
    public static String NO_MENU_FOUND = "No menu found";
    public static String RESTAURANT_INFORMATION_ABSENT = "Restaurant not found and information not provided";
    public static String MENU_ALREADY_EXISTS = "Menu already exists";
    public static String MENU_EXPIRED = "Menu is expired";

    @GetMapping("/users")
    @Operation(summary = "Get all users", security = {@SecurityRequirement(name = "basicScheme")})
    public List<User> getAllUsers() {
        log.info("getAllUsers()");
        return userService.getAll();
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete user", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "User's to be deleted ID.")
    public void deleteUser(@PathVariable Integer id) {
        log.info("deleteUser(@PathVariable Integer id)");
        userService.delete(id);
    }

    @GetMapping("/menus")
    @Operation(summary = "Get menus for specified date", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "date", description = "Optional: Defaults to the next voting date.")
    public List<Menu> getAllMenusByDate(@RequestParam @Nullable Date date) {
        log.info("getAllMenusByDate(@RequestParam @Nullable Date date)");
        date = date == null ? getNextVotingDate() : date;
        return menuRepository.findAllByDate(date);
    }

    @PostMapping("/menus")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new menu", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "menuTo", description = "If no restaurantId specified, a new Restaurant will be created")
    @Transactional
    public ResponseEntity<Menu> addMenu(@Valid @RequestBody MenuTo menuTo) {
        log.info("addMenu(@Valid @RequestBody MenuTo menuTo)");
        menuTo.setDate(menuTo.getDate() == null ? getNextVotingDate() : menuTo.getDate());
        if (menuTo.getDate().before(getNextVotingDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MENU_EXPIRED);
        }
        Restaurant restaurant = menuTo.getRestaurantId() != null ?
                restaurantRepository.findById(menuTo.getRestaurantId()).orElse(null) :
                restaurantRepository.save(new Restaurant(menuTo.getRestaurantName(), menuTo.getRestaurantAddress()));
        if (restaurant == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, RESTAURANT_INFORMATION_ABSENT);
        }
        Menu menu = new Menu(restaurant, menuTo.getDate(), menuTo.getMenuItems());
        if (menuRepository.findByDateAndRestaurant(menu.getDate(), menu.getRestaurant()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, MENU_ALREADY_EXISTS);
        }
        menu = menuRepository.save(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/menus").build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(menu);
    }

    @DeleteMapping("/menus/{id}")
    @Operation(summary = "Delete menu", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "ID of the Menu to delete.")
    @CacheEvict(cacheNames = {"menus"}, allEntries = true)
    public void deleteMenu(@PathVariable Integer id) {
        log.info("deleteMenu(@PathVariable Integer id)");
        menuRepository.deleteById(id);
    }

    @GetMapping("/restaurants")
    @Operation(summary = "Get all the restaurants available", security = {@SecurityRequirement(name = "basicScheme")})
    public List<Restaurant> getAllRestaurants() {
        log.info("getAllRestaurants()");
        return restaurantRepository.findAll();
    }

    @PostMapping("/restaurants")
    @Operation(summary = "Add a new restaurant", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "restaurant", description = "restaurandId shouldn't be specified.")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        log.info("addRestaurant(@Valid @RequestBody Restaurant restaurant)");
        if (restaurant.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, RESTAURANTID_SHOULD_BE_NULL);
        }
        restaurant = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/adin/restaurants").build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurant);
    }

    @PutMapping("/restaurants")
    @Operation(summary = "Update restaurant information", security = {@SecurityRequirement(name = "basicScheme")})
    @CacheEvict(cacheNames = {"menus"}, allEntries = true)
    @Transactional
    public void updateRestaurant(@Valid @RequestBody Restaurant restaurant) {
        log.info("updateRestaurant(@Valid @RequestBody Restaurant restaurant)");
        if (restaurant.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, RESTAURANT_INFORMATION_ABSENT);
        }
        if (restaurantRepository.findById(restaurant.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NO_RESTAURANT_FOUND);
        }
        restaurant = restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/restaurants/{id}")
    @Operation(summary = "Delete restaurant", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "ID of the Restaurant to be deleted.")
    @CacheEvict(cacheNames = {"menus"}, allEntries = true)
    public void deleteRestaurant(@PathVariable Integer id) {
        log.info("deleteRestaurant(@PathVariable Integer id)");
        restaurantRepository.deleteById(id);
    }


    @GetMapping("/votes")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all votes for the specified restaurant on date", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "date", description = "Optional: Defaults to the current date.")
    @Parameter(name = "restaurantId", description = "Optional: ID of the Restaurant. Get votes for all restaurants if empty.")
    public List<Vote> getAllByDateAndRestaurant(@RequestParam @Nullable Date date,
                                                @RequestParam @Nullable Integer restaurantId) {
        log.info("getAllByDateAndRestaurant(Date date, Integer restaurantId)");
        date = date == null ? getCurrentDate() : date;
        Optional<Restaurant> restaurant = Optional.empty();
        if (restaurantId != null) {
            restaurant = restaurantRepository.findById(restaurantId);
            if (restaurant.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_RESTAURANT_FOUND);
            }
        }
        if (restaurant.isEmpty()) {
            List<Vote> list = voteRepository.findAllByDate(date);
            return list;
        } else {
            Optional<Menu> menu = menuRepository.findByDateAndRestaurant(date, restaurant.get());
            if (menu.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_MENU_FOUND);
            }
            return voteRepository.findAllByMenu(menu.get());
        }
    }
}
