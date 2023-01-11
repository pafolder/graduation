package com.pafolder.graduation.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @PostMapping("/reset")
    @Operation(summary = "Reset test database to initial state", security = {@SecurityRequirement(name = "basicScheme")})
    @ResponseStatus(HttpStatus.OK)
    public void resetApplication(@RequestParam String tomcatPortAndFolder) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(TOMCAT_USER, TOMCAT_PASSWORD);
        new RestTemplate().exchange("http://localhost:" + tomcatPortAndFolder + "/manager/text/reload?path=/",
                HttpMethod.GET, new HttpEntity<String>(headers), String.class);
    }
}
