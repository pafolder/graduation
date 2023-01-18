package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.service.UserServiceImpl;
import com.pafolder.graduation.to.UserTo;
import com.pafolder.graduation.validator.UserToValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@Tag(name = "3 register-controller")
@RequestMapping(value = RegisterController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RegisterController extends AbstractController {
    public static final String REST_URL = "/api/register";
    private UserServiceImpl userService;
    protected UserToValidator userToValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userToValidator);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user", security = {@SecurityRequirement(name = "basicScheme")})
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register()");
        User created = userService.save(new User(null,
                userTo.getName(), userTo.getEmail(), userTo.getPassword(), true, User.Role.USER));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
