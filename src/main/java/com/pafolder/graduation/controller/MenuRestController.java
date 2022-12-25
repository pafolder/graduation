package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.security.UserDetails;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class MenuRestController extends AbstractRestController {

    @GetMapping("")
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

    @PostMapping("")
//    public void addMenu(@Valid @RequestBody Menu menu, Authentication authentication) {
    public void addMenu(@Valid @RequestBody Menu menu, @AuthenticationPrincipal UserDetails userDetails) {
//        Object user = authentication.getPrincipal();
        User user = userDetails.getUser();
        log.info("public void addMenu(@Valid @RequestBody Menu menu), Authenticated user: {}", user.getEmail());
        menuService.addMenu(menu);
    }
}
