package com.pafolder.graduation.controller.admin;

import com.pafolder.graduation.controller.AbstractController;
import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.repository.RestaurantRepository;
import com.pafolder.graduation.to.RestaurantTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.pafolder.graduation.controller.admin.AdminRestaurantController.REST_URL;

@RestController
@Tag(name = "5.3 admin-restaurant-controller")
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractController {
    static final String REST_URL = "/api/admin/restaurants";

    private static final String NO_RESTAURANT_FOUND = "No restaurant found";

    RestaurantRepository restaurantRepository;

    @GetMapping
    @Operation(summary = "Get all restaurants", security = {@SecurityRequirement(name = "basicScheme")})
    public List<Restaurant> getAllRestaurants() {
        log.info("getAllRestaurants()");
        return restaurantRepository.findAll();
    }

    @PostMapping
    @Operation(summary = "Add a new restaurant", security = {@SecurityRequirement(name = "basicScheme")})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("addRestaurant()");
        Restaurant saved = restaurantRepository
                .save(new Restaurant(null, restaurantTo.getName(), restaurantTo.getAddress()));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/admin/restaurants/" + saved.getId()).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(saved);
    }

//    @PutMapping("/{id}")
//    @Operation(summary = "Update restaurant information", security = {@SecurityRequirement(name = "basicScheme")})
//    @CacheEvict(cacheNames = {"menus"}, allEntries = true)
//    @Transactional
//    public void updateRestaurant(@PathVariable int id, @Valid @RequestBody RestaurantTo restaurantTo) {
//        log.info("updateRestaurant()");
//        if (restaurantRepository.findById(id).isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_RESTAURANT_FOUND);
//        }
//        restaurantRepository.save(new Restaurant(id, restaurantTo.getName(), restaurantTo.getAddress()));
//    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete restaurant", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "ID of the Restaurant to be deleted.")
    @CacheEvict(cacheNames = {"menus"}, allEntries = true)
    public void deleteRestaurant(@PathVariable int id) {
        log.info("deleteRestaurant()");
        restaurantRepository.deleteById(id);
    }
}
