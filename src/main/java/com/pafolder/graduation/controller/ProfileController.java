package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.repository.UserRepository;
import com.pafolder.graduation.security.UserDetails;
import com.pafolder.graduation.service.UserService;
import com.pafolder.graduation.to.UserTo;
import com.pafolder.graduation.util.UserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "4 Profile", description = "")
@RequestMapping(value = "/api/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {
    private Logger log = LoggerFactory.getLogger(ProfileController.class);
    private UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @Operation(summary = "Get user's credentials", security = {@SecurityRequirement(name = "basicScheme")})
    public User get(@AuthenticationPrincipal UserDetails authUser) {
        return userService.getById(((UserDetails) authUser).getUser().getId()).orElse(null);
    }

    @PutMapping("")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Update user's credentials", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "userTo", description = "Updated user's credentials")
    @Transactional
    public void updateUser(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal UserDetails authUser,
                           HttpServletRequest request) throws ServletException {
        log.info("Update user {}", userTo);
        User userUpdated = UserUtil.createNewFromTo(userTo);
        userUpdated.setId(authUser.getUser().getId());
        userService.save(userUpdated);
        request.logout();
    }

    @DeleteMapping("")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user", security = {@SecurityRequirement(name = "basicScheme")})
    public void haraKiri(@AuthenticationPrincipal UserDetails authUser, HttpServletRequest request) throws ServletException {
        log.error("Self delete user {}", authUser.getUsername());
        userService.delete(authUser.getUser().getId());
        request.logout();
    }
}
