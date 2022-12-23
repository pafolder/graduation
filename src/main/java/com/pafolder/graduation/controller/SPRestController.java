package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.service.SPMenuService;
import com.pafolder.graduation.validator.MenuValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class SPRestController {
    @Autowired
    private MenuValidator menuValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(menuValidator);
    }

    @Autowired
    SPMenuService menuService;

    private Logger log = LoggerFactory.getLogger("yellow");

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
    public void addMenu(@Valid @RequestBody Menu menu) {
        log.info("public void addMenu(@Valid @RequestBody Menu menu)");
        menuService.addMenu(menu);
    }
}
