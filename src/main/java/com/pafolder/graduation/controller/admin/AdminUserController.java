package com.pafolder.graduation.controller.admin;

import com.pafolder.graduation.controller.AbstractController;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pafolder.graduation.controller.AbstractController.REST_URL;
import static com.pafolder.graduation.util.UserUtil.protectPresetUserAndAdmin;

@RestController
@AllArgsConstructor
@Tag(name = "5.2 admin-user-controller")
@RequestMapping(value = REST_URL + "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController extends AbstractController {
    protected UserServiceImpl userService;

    @GetMapping("/")
    @Operation(summary = "Get all users", security = {@SecurityRequirement(name = "basicScheme")})
    public List<User> getAllUsers() {
        log.info("getAllUsers()");
        return userService.getAll();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "User's to be deleted ID")
    public void deleteUser(@PathVariable int id) {
        log.info("deleteUser()");
        protectPresetUserAndAdmin(id);
        userService.delete(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Enable/disable user", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "id", description = "User's to be processed")
    public void updateEnabled(@PathVariable int id, @RequestParam boolean isEnabled) {
        log.info("updateEnabled()");
        protectPresetUserAndAdmin(id);
        userService.updateIsEnabled(id, isEnabled);
    }
}
