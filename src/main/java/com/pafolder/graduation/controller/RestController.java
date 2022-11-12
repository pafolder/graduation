package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RestController {
    MenuService menuService;

    @Autowired
    public RestController(MenuService menuService) {
        this.menuService = menuService;
    }

    public void testDataBase() {
        List<Menu> menuList = menuService.getAll();
        System.out.println(menuList);

    }
}
