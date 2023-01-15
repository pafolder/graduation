package com.pafolder.graduation.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;

import static com.pafolder.graduation.configuration.ApplicationConfiguration.RESTAURANT_VOTING_APPLICATION_SUMMARY;
import static com.pafolder.graduation.util.DateTimeUtil.*;

@Controller
public class UIController {
    private @Value("${springdoc.swagger-ui.path}") String swaggerUiPath;

    @Hidden
    @GetMapping("/")
    public String root() {
        return "forward:login";
    }

    @Hidden
    @GetMapping("/login")
    public String login(@RequestParam @Nullable String error, Model model) {
        model.addAttribute("summary", RESTAURANT_VOTING_APPLICATION_SUMMARY);
        model.addAttribute("currentDate", getCurrentDate());
        model.addAttribute("currentTime", getCurrentTime());
        return "login";
    }

    @GetMapping("/unauthenticated")
    public String unauthorized(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:" + swaggerUiPath + "/index.html";
    }

//    @Hidden
//    @PostMapping("/setdatetime")
//    public String setDateTime(HttpServletRequest request) {
//        LoggerFactory.getLogger("yellow").info("setDateTime");
//        String newDate = request.getParameter("newDate");
//        String newTime = request.getParameter("newTime");
//        setCurrentDate(Date.valueOf(newDate));
//        if (newTime.matches("\\d\\d:\\d\\d")) {
//            setCurrentTime(Time.valueOf(newTime + ":00"));
//        }
//        return "redirect:/";
//    }
}