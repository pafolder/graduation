package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.service.MenuService;
import com.pafolder.graduation.service.RestaurantService;
import com.pafolder.graduation.service.UserService;
import com.pafolder.graduation.service.VoteService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class UIController {
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final UserService userService;
    private final VoteService voteService;
    private final Logger log;

    @Autowired
    public UIController(RestaurantService restaurantService, MenuService menuService, UserService userService,
                        VoteService voteService) {
        this.menuService = menuService;
        this.userService = userService;
        this.voteService = voteService;
        this.restaurantService = restaurantService;
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
//    public ModelAndView getIndexes(HttpServletRequest request, HttpServletResponse response, Model model) {
//        return new ModelAndView("redirect:" + "http://localhost:8080/swagger-ui/index.html");
//    }
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
        Menu menu1 = new Menu(restaurantService.getById(0).orElse(null), Date.valueOf(LocalDate.now()));
        menu1.addItems(new Menu.Item("Hello", 1300.));

        restaurantService.add(new Restaurant("Added", "yyy"));
        List<User> users = userService.getAll();
        List<Restaurant> restaurants = restaurantService.getAll();
        List<Menu> menus = menuService.getAll();
        List<Vote> votes = voteService.getAll();
        menuService.add(menu1);

        List<Menu> menuList = menuService.getAll();
//        System.out.println(menuList);
//        System.out.println("getByDate(\"2020-01-30\"):");
//        System.out.println(menuService.getByDate(Date.valueOf("2020-01-30")));

        User user = new User(0, "Auroutune Karapetovich", "karapetovich@trashedmail.not", User.Role.USER);
        userService.save(user);
    }
}
