package com.pafolder.graduation.controller.admin;

import com.pafolder.graduation.controller.AbstractController;
import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.to.RestaurantTo;
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

@RestController
@Tag(name = "5.3 admin-restaurant-controller")
@RequestMapping(value = REST_URL + "/admin/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractController {
    private static final String NO_RESTAURANT_FOUND = "No restaurant found";
    public static final String CAN_NOT_CHANGE_PRESET_USER_AND_ADMIN = "Changing preset Admin and User isn't allowed in test mode";
    private static final String TOMCAT_MANAGER_RELOAD_PATH = "/manager/text/reload?path=";
    private static final String PROTOCOL_LOCAL_HOST = "http://localhost:";
    private @Value("${rva.tomcat.script.user}") String tomcatUser;
    private @Value("${rva.tomcat.script.password}") String tomcatPassword;

    public static final int ADMIN_ID = 3;
    public static final int USER_ID = 1;

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

    @PutMapping("/{id}")
    @Operation(summary = "Update restaurant information", security = {@SecurityRequirement(name = "basicScheme")})
    @CacheEvict(cacheNames = {"menus"}, allEntries = true)
    @Transactional
    public void updateRestaurant(@PathVariable int id, @Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("updateRestaurant()");
        if (restaurantRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_RESTAURANT_FOUND);
        }
        restaurantRepository.save(new Restaurant(id, restaurantTo.getName(), restaurantTo.getAddress()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete restaurant", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "ID of the Restaurant to be deleted.")
    @CacheEvict(cacheNames = {"menus"}, allEntries = true)
    public void deleteRestaurant(@PathVariable int id) {
        log.info("deleteRestaurant()");
        restaurantRepository.deleteById(id);
    }

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
