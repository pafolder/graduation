package com.pafolder.graduation.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
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
    public String login(@RequestParam @Nullable String error) {
        return "login";
    }

    @Hidden
    @PostMapping("/reset")
    public void restartApplication(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(TOMCAT_USER, TOMCAT_PASSWORD);
        new RestTemplate().exchange("http://localhost:" + request.getLocalPort() + "/manager/text/reload?path=" +
                request.getContextPath(), HttpMethod.GET, new HttpEntity<String>(headers), String.class);
    }
}