package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.pafolder.graduation.controller.AbstractController.REST_URL;
import static com.pafolder.graduation.util.UserUtil.protectPresetUserAndAdmin;

@RestController
@Tag(name = "5 Admin", description = "Administrator's API")
@RequestMapping(value = REST_URL + "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController extends AbstractController {
    private static final String RESTAURANT_ID_SHOULD_BE_NULL = "restaurantId should be null for creating new restaurant";
    private static final String NO_RESTAURANT_FOUND = "No restaurant found";
    public static final String CAN_NOT_CHANGE_PRESET_USER_AND_ADMIN = "Changing preset Admin and User isn't allowed in test mode";
    private static final String TOMCAT_MANAGER_RELOAD_PATH = "/manager/text/reload?path=";
    private static final String PROTOCOL_LOCAL_HOST = "http://localhost:";
    private @Value("${rva.tomcat.script.user}") String tomcatUser;
    private @Value("${rva.tomcat.script.password}") String tomcatPassword;

    public static final int ADMIN_ID = 3;
    public static final int USER_ID = 1;

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
        protectPresetUserAndAdmin(id);
        userService.delete(id);
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Enable/disable user", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "User's to be processed.")
    public void updateEnabledTrue(@PathVariable Integer id, @RequestParam boolean isEnabled) {
        log.info("updateEnabledTrue(@PathVariable Integer id)");
        protectPresetUserAndAdmin(id);
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
    @Parameter(name = "restaurant", description = "restaurantId should be null????? restaurant to ")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        log.info("addRestaurant(@Valid @RequestBody Restaurant restaurant)");
        if (restaurant.getId() != null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, RESTAURANT_ID_SHOULD_BE_NULL);
        }
        restaurant = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/admin/restaurants/" + restaurant.getId()).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurant);
    }

    @PutMapping("/restaurants")
    @Operation(summary = "Update restaurant information", security = {@SecurityRequirement(name = "basicScheme")})
    @CacheEvict(cacheNames = {"menus"}, allEntries = true)
    @Transactional
    public void updateRestaurant(@Valid @RequestBody Restaurant restaurant) {
        log.info("updateRestaurant(@Valid @RequestBody Restaurant restaurant)");
        if (restaurant.getId() == null || restaurantRepository.findById(restaurant.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_RESTAURANT_FOUND);
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

//    @GetMapping("/votes")
//    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Get all votes for the specified restaurant on date", security = {@SecurityRequirement(name = "basicScheme")})
//    @Parameter(name = "date", description = "Optional: Defaults to the current date.")
//    @Parameter(name = "restaurantId", description = "Optional: ID of the Restaurant. Get votes for all restaurants if empty.")
//    public List<Vote> getAllByDateAndRestaurant(@RequestParam @Nullable @NotNull Dat
//                                                @RequestParam @Nullable Integer restaurantId) {
//        log.info("getAllByDateAndRestaurant()");
//        date = date == null ? getCurrentDate() : date;
//        Optional<Restaurant> restaurant = Optional.empty();
//        if (restaurantId != null) {
//            restaurant = restaurantRepository.findById(restaurantId);
//            if (restaurant.isEmpty()) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_RESTAURANT_FOUND);
//            }
//        }
//        if (restaurant.isEmpty()) {
//            return voteRepository.findAllByDate(date);
//        } else {
//            Optional<Menu> menu = menuRepository.findByDateAndRestaurantId(date, restaurant.get().getId());
//            if (menu.isEmpty()) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, MenuController.NO_MENU_FOUND);
//            }
//            return voteRepository.findAllByMenu(menu.get());
//        }
//    }

    @PostMapping("/reset")
    @Operation(summary = "Restart the application, reset the Database", security = {@SecurityRequirement(name = "basicScheme")})
    @ResponseStatus(HttpStatus.OK)
    public void restartApplication(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(tomcatUser, tomcatPassword);
        new RestTemplate().exchange(PROTOCOL_LOCAL_HOST + request.getLocalPort() + TOMCAT_MANAGER_RELOAD_PATH +
                request.getContextPath(), HttpMethod.GET, new HttpEntity<String>(headers), String.class);
    }
}
