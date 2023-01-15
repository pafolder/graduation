package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.to.MenuTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.time.LocalDate;
import java.util.List;

import static com.pafolder.graduation.controller.AbstractController.REST_URL;
import static com.pafolder.graduation.util.DateTimeUtil.getCurrentDate;
import static com.pafolder.graduation.util.DateTimeUtil.getNextVotingDate;

@RestController
@Tag(name = "2 Menus", description = "API")
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController extends AbstractController {
    public static final String NO_MENU_FOUND = "No menu found";
    public static final String NO_RESTAURANT_FOUND = "No restaurant found";
    public static final String MENU_ALREADY_EXISTS = "Menu already exists";
    public static final String MENU_EXPIRED = "Menu is expired";
    public static final String VOTING_IS_OVER = "Voting is over for today";

    @GetMapping("/menus")
    @Operation(summary = "Get menus for current day", security = {@SecurityRequirement(name = "basicScheme")})
    public List<Menu> getAllMenusForCurrentDate() {
        log.info("getAllMenusForCurrentDate()");
        if (!getCurrentDate().equals(getNextVotingDate())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, VOTING_IS_OVER);
        }
        return menuRepository.findAllByDate(getNextVotingDate());
    }

    @PostMapping("/admin/menus")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new menu", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "menuTo", description = "if date not specified, next voting date will be used")
    @Transactional
    public ResponseEntity<Menu> addMenu(@Valid @RequestBody MenuTo menuTo) {
        log.info("addMenu()");
        LocalDate date = menuTo.getDate() == null ? getNextVotingDate() : menuTo.getDate();
        if (date != null && date.isBefore(getNextVotingDate())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, MENU_EXPIRED);
        }
        Restaurant restaurant = menuTo.getRestaurantId() != null ?
                restaurantRepository.findById(menuTo.getRestaurantId()).orElse(null) : null;
        if (restaurant == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_RESTAURANT_FOUND);
        }
        Menu menu = new Menu(restaurant, date, menuTo.getMenuItems());
        if (menuRepository.findByDateAndRestaurantId(menu.getDate(), menu.getRestaurant().getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, MENU_ALREADY_EXISTS);
        }
        menu = menuRepository.save(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/menus").build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(menu);
    }

    @DeleteMapping("/admin/menus/{id}")
    @Operation(summary = "Delete menu", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "ID of the Menu to delete.")
    @CacheEvict(cacheNames = {"menus"}, allEntries = true)
    public void deleteMenu(@PathVariable Integer id) {
        log.info("deleteMenu(@PathVariable Integer id)");
        menuRepository.deleteById(id);
    }
}
