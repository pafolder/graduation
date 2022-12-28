package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.security.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

@RestController
@Tag(name = "Votes", description = "The Graduation Menus API")
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractController {

    static final Date CURRENT_DATE = Date.valueOf("2020-02-01");

//    @GetMapping("/votes")
//    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
//    @ResponseStatus(HttpStatus.OK)
//    public List<Vote> getAll(@AuthenticationPrincipal UserDetails userDet) {
//        List<Vote> votes = voteService.getAll();
//        return votes;
//    }

    @GetMapping("/votes")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> getAllByDate(@RequestParam @Nullable Date date, @AuthenticationPrincipal UserDetails userDet) {
        return date == null ? voteService.getAll() : voteService.getAllByDate(date);
    }

    @PostMapping("/vote")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Vote accepted")
    public void acceptVote(@RequestParam int menuId, HttpServletResponse response,
                           @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        log.info("User {} voting for the {} menu", userDetails.getUsername(), menuId);
        Date date = CURRENT_DATE;
        Menu menu = menuService.getById(menuId).orElse(null);
        if (menu == null || !menu.getDate().equals(CURRENT_DATE)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No such Menu found");
        }
        Vote vote = new Vote(date, userDetails.getUser(), menu);
        voteService.add(vote);
    }
}
