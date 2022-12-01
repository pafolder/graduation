package com.pafolder.graduation.web;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.service.MenuService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
//public class MenuRestController extends AbstractRestController {
//    private MenuService menuService;
//
//    public MenuRestController(MenuService menuService) {
//        this.menuService = menuService;
//    }
//
//    @GetMapping("/rest/menus")
//    List<Menu> getAll() {
//        return menuService.getAll();
//    }
//}
