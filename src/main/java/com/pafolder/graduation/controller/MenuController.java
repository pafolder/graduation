package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.security.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static com.pafolder.graduation.util.DateTimeUtil.getCurrentDate;

@RestController
@Tag(name = "2 Menus", description = "Menus API")
@RequestMapping("/api")
public class MenuController extends AbstractController {

    @GetMapping("/menus")
    @ResponseStatus(HttpStatus.OK)
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public List<Menu> getAll(@RequestParam @Nullable Date date) {
        return menuRepository.findAllByDate(date == null ? getCurrentDate() : date);
    }

//    @PostMapping("/menus")
//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
//    public void addMenu(@Valid @RequestBody Menu menu, @AuthenticationPrincipal UserDetails userDetails) {
//        User user = userDetails.getUser();
//        log.info("public void addMenu(@Valid @RequestBody Menu menu), Authenticated user: {}", user.getEmail());
//        menuRepository.save(menu);
//    }
}
