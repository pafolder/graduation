package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.validator.MenuValidator;
import com.pafolder.graduation.validator.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Profile", description = "Administration API")
@RequestMapping("/rest/admin")
public class AdminController extends AbstractController {
    private final MenuValidator menuValidator;
    private final UserValidator userValidator;

    public AdminController(MenuValidator menuValidator, UserValidator userValidator) {
        this.menuValidator = menuValidator;
        this.userValidator = userValidator;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(menuValidator);
        binder.setValidator(userValidator);
    }

    @GetMapping("/users")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAll() {
        return userService.getAll();
    }
}
