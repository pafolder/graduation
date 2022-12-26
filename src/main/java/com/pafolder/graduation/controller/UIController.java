package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.security.UserDetails;
import com.pafolder.graduation.service.MenuService;
import com.pafolder.graduation.service.UserService;
import com.pafolder.graduation.service.VoteService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class UIController {
    private final MenuService menuService;
    private final UserService userService;
    private final VoteService voteService;
    private final Logger log;

    @Autowired
    public UIController(MenuService menuService, UserService userService, VoteService voteService) {
        this.menuService = menuService;
        this.userService = userService;
        this.voteService = voteService;
        log = LoggerFactory.getLogger("yellow");
        log.error("Hello! Logging has started!");
        testDataBase();
    }

    @GetMapping("/login")
    public String login() {
        log.error("login");
        return "login";
    }

//   @GetMapping("/error")
//    public String error(@RequestParam String message, Model model) {
//        log.error("=== /error: " + message);
//       List<Menu> menus = menuService.getAll();
//       model.addAttribute("menus", menus);
//        return "jsp/menus";
//    }

//    @GetMapping("/")
//    public String getIndexes(HttpServletRequest request, HttpServletResponse response, Model model) {
//        log.error("@GetMapping('/')");
//        Cookie [] cookies = request.getCookies();
//        if( cookies.length == 0 ) {
//            Cookie cookie = new Cookie("username", "petr_p@yandex.com");
//            Cookie cookie2 = new Cookie("password", "password");
//            response.addCookie(cookie);
//            response.addCookie(cookie2);
//        } else {
//            for (Cookie cookie: cookies) {
//                log.info(cookie.getName() + "=" + cookie.getValue());
//            }
//        }
//        UserDetails currentUser = null;
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            currentUser = (UserDetails) (authentication.getPrincipal());
//        }
//
//        User user = currentUser.getUser();
//
//        List<Menu> menus = menuService.getAll();
//        model.addAttribute("menus", menus);
//        model.addAttribute("currentUser", currentUser.getUser().getEmail());
//        return "jsp/menus";
//    }

    @GetMapping("/root")
    @ResponseStatus(HttpStatus.OK)
    public String getRoot(HttpServletRequest request) {
        log.error("@GetMapping('/root')");
        return "thymeleafPage";
    }

    @Hidden
    @GetMapping("/menus")
    public String getAll(HttpServletRequest request, Model model) {
        log.error("@GetMapping('/menus')");
        List<Menu> menus = menuService.getAll();
        model.addAttribute("menus", menus);
        return "jsp/menus";
    }

    public void testDataBase() {
//        menuService.addItem(100000, "Hello", 1300.);
//        Menu menu = new Menu("Меню Пятого Ресторана", Date.valueOf(LocalDate.now()));
//        Menu menu2 = new Menu("Меню Пятого Ресторана", Date.valueOf(LocalDate.now()));
        Menu menu1 = new Menu("Меню Четвёртого Ресторана", Date.valueOf(LocalDate.now()));
        menu1.addItems(new Menu.Item("Hello", 1300.));
        menuService.addMenu(menu1);
//        menuService.create(menu);

        List<Menu> menuList = menuService.getAll();
        System.out.println(menuList);
        System.out.println("getByDate(\"2020-01-30\"):");
        System.out.println(menuService.getByDate(Date.valueOf("2020-01-30")));

        User user = new User(0, "Auroutune Karapetovich", "karapetovich@trashedmail.not", User.Role.CLIENT);
        userService.save(user);
        List<User> users = userService.getAll();
        System.out.println(users);


//        Vote vote = new Vote(Date.valueOf(LocalDate.now()), users.get(2), menuList.get(3));
//        voteService.save(vote);
        List<Vote> votes = voteService.getAll();
        System.out.println(votes);
    }
}
