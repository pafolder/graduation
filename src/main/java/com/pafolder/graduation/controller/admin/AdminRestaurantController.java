package com.pafolder.graduation.controller.admin;

import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.repository.RestaurantRepository;
import com.pafolder.graduation.repository.VoteRepository;
import com.pafolder.graduation.to.RestaurantTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.pafolder.graduation.controller.admin.AdminMenuController.NO_RESTAURANT_FOUND;
import static com.pafolder.graduation.controller.admin.AdminRestaurantController.REST_URL;

@RestController
@AllArgsConstructor
@Tag(name = "5.3 admin-restaurant-controller")
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {
    public static final String REST_URL = "/api/admin/restaurants";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private RestaurantRepository restaurantRepository;
    private VoteRepository voteRepository;

    @GetMapping
    @Operation(summary = "Get all restaurants", security = {@SecurityRequirement(name = "basicScheme")})
    public List<Restaurant> getAll() {
        log.info("getAll()");
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}/vote-count")
    @Operation(summary = "Get vote count for restaurant", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "Restaurant Id to get today's vote count")
    public int getVoteCount(@PathVariable int id) {
        log.info("getVoteCount()");
        return voteRepository.countByVoteDateAndRestaurantId(LocalDate.now(), id);
    }

    @PostMapping
    @CacheEvict(value = "menus", allEntries = true)
    @Operation(summary = "Create a new restaurant", security = {@SecurityRequirement(name = "basicScheme")})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("create()");
        Restaurant createdRestaurant = restaurantRepository
                .save(new Restaurant(null, restaurantTo.getName(), restaurantTo.getAddress()));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(createdRestaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createdRestaurant);
    }

    @PutMapping
    @CacheEvict(value = "menus", allEntries = true)
    @Operation(summary = "Update restaurant", security = {@SecurityRequirement(name = "basicScheme")})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestParam int restaurantId, @Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("update()");
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_RESTAURANT_FOUND));
        restaurant.setId(restaurantId);
        if (restaurantTo.getName() != null) {
            restaurant.setName(restaurantTo.getName());
        }
        if (restaurantTo.getAddress() != null) {
            restaurant.setAddress(restaurantTo.getAddress());
        }
        restaurantRepository
                .save(new Restaurant(null, restaurantTo.getName(), restaurantTo.getAddress()));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "menus", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete restaurant", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "Id of the Restaurant to delete")
    public void delete(@PathVariable int id) {
        log.info("delete()");
        restaurantRepository.deleteById(id);
    }
}
