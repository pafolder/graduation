package com.pafolder.graduation.controller.admin;

import com.pafolder.graduation.controller.AbstractController;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.pafolder.graduation.controller.admin.AdminRestaurantController.REST_URL;

@RestController
@AllArgsConstructor
@Tag(name = "5.3 admin-restaurant-controller")
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractController {
    public static final String REST_URL = "/api/admin/restaurants";
    private RestaurantRepository restaurantRepository;
    private VoteRepository voteRepository;

    @GetMapping
    @Operation(summary = "Get all restaurants", security = {@SecurityRequirement(name = "basicScheme")})
    public List<Restaurant> getRestaurants() {
        log.info("getRestaurants()");
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
    @Operation(summary = "Add a new restaurant", security = {@SecurityRequirement(name = "basicScheme")})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("addRestaurant()");
        Restaurant createdRestaurant = restaurantRepository
                .save(new Restaurant(null, restaurantTo.getName(), restaurantTo.getAddress()));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(createdRestaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createdRestaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete restaurant", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "Id of the Restaurant to delete")
    @CacheEvict(cacheNames = {"menus"}, allEntries = true)
    public void deleteRestaurant(@PathVariable int id) {
        log.info("deleteRestaurant()");
        restaurantRepository.deleteById(id);
    }
}
