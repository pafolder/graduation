package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

import static com.pafolder.graduation.util.DateTimeUtil.getNextVotingDate;

@RestController
@Tag(name = "2 Menus", description = "API")
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController extends AbstractController {
    private static String ILLEGAL_DATE_FOR_GETTING_MENU = "Illegal date for getting menus for voting (in the past)";

    @GetMapping("/menus")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get actual menus for voting", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "date", description = "Optional: Date to get menus for. Defaults to the next launch date.")
    public List<Menu> getAll(@RequestParam @Nullable Date date) {
        date = date == null ? getNextVotingDate() : date;
        if (date.after(getNextVotingDate())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ILLEGAL_DATE_FOR_GETTING_MENU);
        }
        return menuRepository.findAllByDate(date);
    }
}
