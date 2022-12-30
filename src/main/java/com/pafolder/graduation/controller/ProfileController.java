package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.repository.UserRepository;
import com.pafolder.graduation.security.UserDetails;
import com.pafolder.graduation.to.UserTo;
import com.pafolder.graduation.util.UserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "4 Profile", description = "Change user information")
@RequestMapping("/api/profile")
public class ProfileController {
    private Logger log = LoggerFactory.getLogger(ProfileController.class);
    private UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public User get(@AuthenticationPrincipal UserDetails authUser) {
        return ((UserDetails) authUser).getUser();
    }

    @PutMapping("")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public void updateUser(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal UserDetails authUser) {
        log.info("Update user {}", userTo);
        User userUpdated = UserUtil.createNewFromTo(userTo);
        userUpdated.setId(authUser.getUser().getId());
        userRepository.save(userUpdated);
    }
}
