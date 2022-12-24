package com.pafolder.graduation.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class UIController {
//    private final MenuService menuService;
//    private final UserService userService;
//    private final VoteService voteService;
    private final Logger log;

    @Autowired
    public UIController() {
//        this.menuService = menuService;
//        this.userService = userService;
//        this.voteService = voteService;
        log = LoggerFactory.getLogger("yellow");
        log.error("Hello! Logging has started!");
//        testDataBase();
    }



//   @GetMapping("/error")
//    public String error(@RequestParam String message, Model model) {
//        log.error("=== /error: " + message);
//       List<Menu> menus = menuService.getAll();
//       model.addAttribute("menus", menus);
//        return "jsp/menus";
//    }

        @GetMapping("/main")
    public String getIndexes(HttpServletRequest request, HttpServletResponse response, Model model) {
        log.error("@GetMapping('/')");
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
//            UserDetails currentUser = null;

//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (!(authentication instanceof AnonymousAuthenticationToken)) {
//                 currentUser = (UserDetails) (authentication.getPrincipal());
//            }
//
//            User user = currentUser.getUser();
//
//            List<Menu> menus = menuService.getAll();
//            model.addAttribute("menus", menus);
//            model.addAttribute("currentUser", currentUser.getUser().getEmail());
        return "jsp/menus";
    }


    @GetMapping("/menus")
    public String getAll(HttpServletRequest request, Model model) {
        log.error("@GetMapping('/menus')");
//        List<Menu> menus = menuService.getAll();
//        model.addAttribute("menus", menus);
        return "jsp/menus";
    }


}
