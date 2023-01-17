package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.security.UserDetailsImpl;
import com.pafolder.graduation.service.UserServiceImpl;
import com.pafolder.graduation.to.UserTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "4 profile-controller")
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController extends AbstractController {
    static final String REST_URL = "/api/profile";
    UserServiceImpl userService;

    @GetMapping
    @Operation(summary = "Get authenticated user's credentials", security = {@SecurityRequirement(name = "basicScheme")})
    public User get(@AuthenticationPrincipal UserDetailsImpl authUser) {
        log.info("Getting authenticated user ({}) credentials", authUser.getUser().getEmail());
        return userService.getById(authUser.getUser().getId()).orElse(null);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Update authenticated user's credentials", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "userTo", description = "Updated user's credentials")
    @Transactional
    public void updateUser(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal UserDetailsImpl authUser,
                           HttpServletRequest request) throws ServletException {
        log.info("updateUser()");
        int id = authUser.getUser().getId();
        protectPresetAdmin(id);
        User updated = new User(null,
                userTo.getName(), userTo.getEmail(), userTo.getPassword(), true, User.Role.USER);
        updated.setId(id);
        userService.save(updated);
        request.logout();
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete authenticated user", security = {@SecurityRequirement(name = "basicScheme")})
    public void selfDelete(@AuthenticationPrincipal UserDetailsImpl authUser, HttpServletRequest request) throws ServletException {
        log.error("Self deleting user {}", authUser.getUsername());
        int id = authUser.getUser().getId();
        protectPresetAdmin(id);
        userService.delete(id);
        request.logout();
    }
}
