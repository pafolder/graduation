package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.repository.MenuRepository;
import com.pafolder.graduation.repository.RestaurantRepository;
import com.pafolder.graduation.repository.VoteRepository;
import com.pafolder.graduation.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class UIController {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final UserService userService;
    private final VoteRepository voteRepository;
    private final Logger log;

    @Autowired
    public UIController(RestaurantRepository restaurantRepository, MenuRepository menuRepository, UserService userService,
                        VoteRepository voteRepository) {
        this.menuRepository = menuRepository;
        this.userService = userService;
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
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
        List<Menu> menus = menuRepository.findAll();
        model.addAttribute("menus", menus);
        return "jsp/menus";
    }

    public void testDataBase() {

//        List<Menu> menuList = menuRepository.findAll();
//        System.out.println(menuList);
//        System.out.println("getByDate(\"2020-01-30\"):");
//        System.out.println(menuService.getByDate(Date.valueOf("2020-01-30")));

//        User user = new User(0, "Auroutune Karapetovich", "karapetovich@trashedmail.not", "password", User.Role.USER);
//        userService.save(user);
    }
}
