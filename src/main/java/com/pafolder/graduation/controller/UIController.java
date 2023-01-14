package com.pafolder.graduation.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.sql.Time;

import static com.pafolder.graduation.configuration.ApplicationConfiguration.RESTAURANT_VOTING_APPLICATION_SUMMARY;
import static com.pafolder.graduation.util.DateTimeUtil.*;

@Controller
public class UIController {
    private static final String TOMCAT_MANAGER_RELOAD_PATH = "/manager/text/reload?path=";
    private static final String PROTOCOL_LOCAL_HOST = "http://localhost:";
    private @Value("${springdoc.swagger-ui.path}") String swaggerUiPath;
    private @Value("${rva.tomcat.script.user}") String tomcatUser;
    private @Value("${rva.tomcat.script.password}") String tomcatPassword;

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

    @Hidden
    @PostMapping("/setdatetime")
    public String setDateTime(HttpServletRequest request) {
        LoggerFactory.getLogger("yellow").info("setDateTime");
        String newDate = request.getParameter("newDate");
        String newTime = request.getParameter("newTime");
        setCurrentDate(Date.valueOf(newDate));
        if (newTime.matches("\\d\\d:\\d\\d")) {
            setCurrentTime(Time.valueOf(newTime + ":00"));
        }
        return "redirect:/";
    }

    @Hidden
    @PostMapping("/reset")
    public String restartApplication(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(tomcatUser, tomcatPassword);
        new RestTemplate().exchange(PROTOCOL_LOCAL_HOST + request.getLocalPort() + TOMCAT_MANAGER_RELOAD_PATH +
                request.getContextPath(), HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        return "redirect:/";
    }
}