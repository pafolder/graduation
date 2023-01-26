package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.security.UserDetailsImpl;
import com.pafolder.graduation.service.UserServiceImpl;
import com.pafolder.graduation.to.UserTo;
import com.pafolder.graduation.util.ControllerUtil;
import com.pafolder.graduation.validator.UserToValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "4 profile-controller")
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController extends ControllerUtil {
    public static final String REST_URL = "/api/profile";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private UserServiceImpl userService;
    protected UserToValidator userToValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userToValidator);
    }

    @GetMapping
    @Operation(summary = "Get authenticated user's credentials", security = {@SecurityRequirement(name = "basicScheme")})
    public User getAuth(@AuthenticationPrincipal UserDetailsImpl authUser) {
        log.info("getAuth()");
        return userService.getById(authUser.getUser().getId()).orElse(null);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Update authenticated user's credentials",
            security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "userTo", description = "Updated user's credentials")
    @Transactional
    public void updateAuth(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal UserDetailsImpl authUser,
                           HttpServletRequest request) throws ServletException {
        log.info("updateAuth()");
        int id = authUser.getUser().getId();
        protectAdminPreset(id);
        User updated = new User(id, userTo.getName(), userTo.getEmail(), userTo.getPassword(),
                true, User.Role.USER);
        userService.save(updated);
        request.logout();
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete authenticated user", security = {@SecurityRequirement(name = "basicScheme")})
    public void deleteAuth(@AuthenticationPrincipal UserDetailsImpl authUser, HttpServletRequest request) throws ServletException {
        log.info("deleteAuth()");
        int id = authUser.getUser().getId();
        protectAdminPreset(id);
        userService.delete(id);
        request.logout();
    }
}
