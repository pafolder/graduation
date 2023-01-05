package com.pafolder.graduation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController {
    @GetMapping("/")
    public String login() {
        return "login";
    }
}
