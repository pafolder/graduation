package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.repository.MenuRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static com.pafolder.graduation.util.DateTimeUtil.isLateToVote;

@RestController
@AllArgsConstructor
@Tag(name = "2 menu-controller")
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController extends AbstractController {
    public static final String REST_URL = "/api/profile/menus";
    static final String VOTING_IS_OVER = "Voting is over for today";
    protected MenuRepository menuRepository;

    @GetMapping
    @Operation(summary = "Get today's menu list for voting", security = {@SecurityRequirement(name = "basicScheme")})
    public List<Menu> getAllMenusForCurrentDate() {
        log.info("getAllMenusForCurrentDate()");
        if (isLateToVote()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, VOTING_IS_OVER);
        }
        return menuRepository.findAllByDate(LocalDate.now());
    }
}
