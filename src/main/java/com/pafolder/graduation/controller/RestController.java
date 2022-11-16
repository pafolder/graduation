package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.service.MenuService;
import com.pafolder.graduation.service.UserService;
import com.pafolder.graduation.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Date;
import java.util.List;

@Controller
public class RestController {
    private MenuService menuService;
    private UserService userService;
    private VoteService voteService;

    @Autowired
    public RestController(MenuService menuService, UserService userService, VoteService voteService) {
        this.menuService = menuService;
        this.userService = userService;
        this.voteService = voteService;
    }

    public void testDataBase() {
//        menuService.addItem(100000, "Hello", 1300.);
//        Menu menu = new Menu("Меню Пятого Ресторана", Date.valueOf(LocalDate.now()));
//        Menu menu2 = new Menu("Меню Пятого Ресторана", Date.valueOf(LocalDate.now()));
//        Menu menu1 = new Menu("Меню Четвёртого Ресторана", Date.valueOf(LocalDate.now()));
//        menuService.create(menu1);
//        menuService.create(menu);

        List<Menu> menuList = menuService.getAll();
        System.out.println(menuList);
        System.out.println("getByDate(\"2020-01-30\"):");
        System.out.println(menuService.getByDate(Date.valueOf("2020-01-30")));

        User user = new User("Auroutune Karapetovich", "karapetovich@trashedmail.not", User.Role.CLIENT);
        userService.save(user);
        List<User> users = userService.getAll();
        System.out.println(users);

//        Vote vote = new Vote(Date.valueOf(LocalDate.now()), users.get(2), menuList.get(3));
//        voteService.save(vote);
        List<Vote> votes = voteService.getAll();
        System.out.println(votes);
    }
}
