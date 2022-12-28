package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.security.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Menus", description = "The Graduation Menus API")
@RequestMapping("/rest")
public class MenuController extends AbstractController {

    @GetMapping("/menus")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    @ResponseStatus(HttpStatus.OK)
    public List<Menu> getAll() {
        List<Menu> list = menuService.getAll();
//        for (Menu menu : list) {
//            log.info("Restaurant {}", menu.getRestaurant());
//            for (Menu.Item item : menu.getMenuItems()) {
//            log.info("item: {} price {}", item.getDishName(), item.getDishPrice());
//            }
//        }
        return list;
    }

    @PostMapping("/menus")
    @ResponseStatus(HttpStatus.CREATED)
//    public void addMenu(@Valid @RequestBody Menu menu, Authentication authentication) {
    public void addMenu(@Valid @RequestBody Menu menu, @AuthenticationPrincipal UserDetails userDetails) {
//        Object user = authentication.getPrincipal();
        User user = userDetails.getUser();
        log.info("public void addMenu(@Valid @RequestBody Menu menu), Authenticated user: {}", user.getEmail());
        menuService.add(menu);
    }
}
