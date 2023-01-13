package com.pafolder.graduation.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static com.pafolder.graduation.configuration.ApplicationConfiguration.RESTAURANT_VOTING_APPLICATION_SUMMARY;

@Controller
public class UIController {
    private @Value("${rva.tomcat.script.user}") String tomcatUser;
    private @Value("${rva.tomcat.script.password}") String tomcatPassword;

    @GetMapping("/")
    public String root() {
        return "forward:login";
    }

    @GetMapping("/login")
    public String login(@RequestParam @Nullable String error, Model model) {
        model.addAttribute("summary", RESTAURANT_VOTING_APPLICATION_SUMMARY);
        return "login";
    }

    private @Value("${springdoc.swagger-ui.path}") String swaggerUiPath;

    @GetMapping("/unauthorized")
    public String unauthorized(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:" + swaggerUiPath + "/index.html";
    }

    @Hidden
    @PostMapping("/reset")
    public void restartApplication(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(tomcatUser, tomcatPassword);
        new RestTemplate().exchange("http://localhost:" + request.getLocalPort() + "/manager/text/reload?path=" +
                request.getContextPath(), HttpMethod.GET, new HttpEntity<String>(headers), String.class);
    }
}