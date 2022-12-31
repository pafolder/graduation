package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.repository.RestaurantRepository;
import com.pafolder.graduation.security.UserDetails;
import com.pafolder.graduation.to.MenuTo;
import com.pafolder.graduation.validator.MenuToValidator;
import com.pafolder.graduation.validator.UserToValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static com.pafolder.graduation.util.DateTimeUtil.getCurrentDate;

@RestController
@Tag(name = "5 Admin", description = "Administration API")
@RequestMapping("/api/admin")
public class AdminController extends AbstractController {
    private final MenuToValidator menuToValidator;
    private final UserToValidator userToValidator;
    private final RestaurantRepository restaurantRepository;

    public AdminController(MenuToValidator menuToValidator, UserToValidator userToValidator,
                           RestaurantRepository restaurantRepository) {
        this.menuToValidator = menuToValidator;
        this.userToValidator = userToValidator;
        this.restaurantRepository = restaurantRepository;
    }

    @InitBinder
    private void initBinderMenuTo(WebDataBinder binder) {
//        binder.setValidator(menuToValidator);
        binder.addValidators(menuToValidator, userToValidator);
    }

//    @InitBinder("userTo")
//    private void initBinderUserTo(WebDataBinder binder) {
//        binder.setValidator(userToValidator);
//    }

    @GetMapping("/users")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/menus")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public List<Menu> getAllMenusByDate(@RequestParam @Nullable Date date) {
        date = date == null ? getCurrentDate() : date;
        return menuRepository.findAllByDate(date);
    }

    @PostMapping("/menus")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Menu> addMenu(@Valid @RequestBody MenuTo menuTo) {
        menuTo.setDate(menuTo.getDate() == null ? getCurrentDate() : menuTo.getDate());
        Restaurant restaurant = menuTo.getRestaurantId() != null ?
                restaurantRepository.getReferenceById(menuTo.getRestaurantId()) :
                restaurantRepository.save(new Restaurant(menuTo.getRestaurantName(), menuTo.getRestaurantAddress()));
        Menu menu = new Menu(restaurant, menuTo.getDate(), menuTo.getMenuItems());
        menu = menuRepository.save(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/menus").build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(menu);
    }

    @DeleteMapping("/menus/{id}")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public void deleteMenu(@PathVariable Integer id) {
        menuRepository.deleteById(id);
    }

    @GetMapping("/restaurants")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @PostMapping("/restaurants")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        if (restaurant.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "restaurantId should be null for creating new restaurant");
        }
        restaurant = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/adin/restaurants").build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurant);
    }

    @PutMapping("/restaurants")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public void updateRestaurant(@Valid @RequestBody Restaurant restaurant) {
        restaurant = restaurantRepository.save(restaurant);
        if (restaurant.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide restaurantId for updating restaurant");
        }
    }

    @DeleteMapping("/restaurants/{id}")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public void deleteRestaurant(@PathVariable Integer id) {
        restaurantRepository.deleteById(id);
    }

    @GetMapping("/votes")
    @ResponseStatus(HttpStatus.OK)
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public List<Vote> getAllByDateAndRestaurant(@RequestParam @Nullable Date date,
                                                @RequestParam @Nullable Integer restaurantId) {
        date = date == null ? getCurrentDate() : date;
        Optional<Restaurant> restaurant = Optional.empty();
        if (restaurantId != null) {
            restaurant = restaurantRepository.findById(restaurantId);
            if (restaurant.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No restaurant found");
            }
        }
        if( restaurant.isEmpty()) {
            return voteRepository.findAllByDate(date);
        } else {
            Optional<Menu> menu = menuRepository.findByDateAndRestaurant(date, restaurant.get());
            if (menu.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No menu found");
            }
        return voteRepository.findByMenu(menu.get());
        }
    }

}
