package com.pafolder.graduation.controller;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UIController {
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
}
