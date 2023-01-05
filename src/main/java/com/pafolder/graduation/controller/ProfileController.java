package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.security.UserDetails;
import com.pafolder.graduation.to.UserTo;
import com.pafolder.graduation.util.UserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.pafolder.graduation.controller.AbstractController.REST_URL;

@RestController
@Tag(name = "4 Profile", description = "of the authenticated user")
@RequestMapping(value = REST_URL + "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController extends AbstractController {

    @GetMapping("")
    @Operation(summary = "Get authenticated user's credentials", security = {@SecurityRequirement(name = "basicScheme")})
    public User get(@AuthenticationPrincipal UserDetails authUser) {
        log.info("get(@AuthenticationPrincipal UserDetails authUser)");
        log.info("Getting authenticated user ({}) credentials", authUser.getUser().getEmail());
        return userService.getById(((UserDetails) authUser).getUser().getId()).orElse(null);
    }

    @PutMapping("")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Update authenticated user's credentials", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "userTo", description = "Updated user's credentials")
    @Transactional
    public void updateUser(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal UserDetails authUser,
                           HttpServletRequest request) throws ServletException {
        log.info("Updating authenticated user {}", userTo);
        User userUpdated = UserUtil.createNewFromTo(userTo);
        userUpdated.setId(authUser.getUser().getId());
        userService.save(userUpdated);
        request.logout();
    }

    @DeleteMapping("")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete authenticated user", security = {@SecurityRequirement(name = "basicScheme")})
    public void haraKiri(@AuthenticationPrincipal UserDetails authUser, HttpServletRequest request) throws ServletException {
        log.error("Self deleting user {}", authUser.getUsername());
        userService.delete(authUser.getUser().getId());
        request.logout();
    }
}
