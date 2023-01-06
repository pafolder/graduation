package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
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

@RestController
@Tag(name = "5 Admin", description = "Administrator's API")
@RequestMapping(value = REST_URL + "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController extends AbstractController {
    private static final String RESTAURANTID_SHOULD_BE_NULL = "restaurantId should be null for creating new restaurant";
    private static final String NO_RESTAURANT_FOUND = "No restaurant found";

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

    @PutMapping("/users/{id}")
    @Operation(summary = "Enable/disable user", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "User's to be processed.")
    public void updateEnabledTrue(@PathVariable Integer id, @RequestParam boolean isEnabled) {
        log.info("updateEnabledTrue(@PathVariable Integer id)");
        userService.updateIsEnabled(id, isEnabled);
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
        if (restaurant.getId() == null || restaurantRepository.findById(restaurant.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NO_RESTAURANT_FOUND);
        }
        restaurantRepository.save(restaurant);
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
            return voteRepository.findAllByDate(date);
        } else {
            Optional<Menu> menu = menuRepository.findByDateAndRestaurant(date, restaurant.get());
            if (menu.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, MenuController.NO_MENU_FOUND);
            }
            return voteRepository.findAllByMenu(menu.get());
        }
    }
}
