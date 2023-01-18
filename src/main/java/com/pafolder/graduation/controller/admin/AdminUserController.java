package com.pafolder.graduation.controller.admin;

import com.pafolder.graduation.controller.AbstractController;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pafolder.graduation.controller.admin.AdminUserController.REST_URL;

@RestController
@AllArgsConstructor
@Tag(name = "5.1 admin-users-controller")
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController extends AbstractController {
    public static final String REST_URL = "/api/admin/users";
    protected UserServiceImpl userService;

    @GetMapping
    @Operation(summary = "Get all users", security = {@SecurityRequirement(name = "basicScheme")})
    public List<User> getAllUsers() {
        log.info("getAllUsers()");
        return userService.getAll();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Enable/disable user", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "User's to be enabled/disabled Id")
    public void updateEnabled(@PathVariable int id, @RequestParam boolean isEnabled) {
        log.info("updateEnabled()");
        protectAdminPreset(id);
        userService.updateIsEnabled(id, isEnabled);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "User to be deleted Id")
    public void deleteUser(@PathVariable int id) {
        log.info("deleteUser()");
        protectAdminPreset(id);
        userService.delete(id);
    }
}
