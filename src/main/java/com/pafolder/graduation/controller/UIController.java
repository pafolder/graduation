package com.pafolder.graduation.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class UIController {
    private static final String TOMCAT_USER = "user";
    private static final String TOMCAT_PASSWORD = "password";

    @GetMapping("/")
    public String root() {
        return "login";
    }

    @GetMapping("/login")
    public String login(@RequestParam @Nullable String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "User not found");
        }
        return "login";
    }

    @Hidden
    @DeleteMapping("/")
    public void restartApplication() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(TOMCAT_USER, TOMCAT_PASSWORD);
        new RestTemplate().exchange("http://localhost/manager/text/reload?path=/",
                HttpMethod.GET, new HttpEntity<String>(headers), String.class);
    }
}
