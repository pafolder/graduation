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

import java.util.List;

import static com.pafolder.graduation.controller.AbstractController.REST_URL;
import static com.pafolder.graduation.util.DateTimeUtil.getCurrentDate;
import static com.pafolder.graduation.util.DateTimeUtil.getNextVotingDate;

@RestController
@AllArgsConstructor
@Tag(name = "2 Menus", description = "API")
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController extends AbstractController {
    protected MenuRepository menuRepository;

    public static final String VOTING_IS_OVER = "Voting is over for today";

    @GetMapping("/menus")
    @Operation(summary = "Get menus for current day", security = {@SecurityRequirement(name = "basicScheme")})
    public List<Menu> getAllMenusForCurrentDate() {
        log.info("getAllMenusForCurrentDate()");
        if (!getCurrentDate().equals(getNextVotingDate())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, VOTING_IS_OVER);
        }
        return menuRepository.findAllByDate(getNextVotingDate());
    }

}
