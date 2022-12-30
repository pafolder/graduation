package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.to.MenuTo;
import com.pafolder.graduation.validator.MenuToValidator;
import com.pafolder.graduation.validator.UserToValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static com.pafolder.graduation.util.DateTimeUtil.getCurrentDate;

@RestController
@Tag(name = "5 Admin", description = "Administration API")
@RequestMapping("/api/admin")
public class AdminController extends AbstractController {
    private final MenuToValidator menuToValidator;
    private final UserToValidator userToValidator;

    public AdminController(MenuToValidator menuToValidator, UserToValidator userToValidator) {
        this.menuToValidator = menuToValidator;
        this.userToValidator = userToValidator;
    }

    @InitBinder("menuTo")
    private void initBinderMenuTo(WebDataBinder binder) {
        binder.setValidator(menuToValidator);
    }

    @InitBinder("userTo")
    private void initBinderUserTo(WebDataBinder binder) {
        binder.setValidator(userToValidator);
    }

    @GetMapping("/users")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/menus")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public List<Menu> getAllMenusByDate(@RequestParam @Nullable Date date) {
        date = date == null ? getCurrentDate() : date;
        return menuRepository.findAllByDate(date);
    }

    @PostMapping("/menus")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public void addMenu(@Valid @RequestBody MenuTo menuTo) {
        log.error("addMenu got: ",menuTo.getName());
    }
}
