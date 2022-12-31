package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.security.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.Optional;

import static com.pafolder.graduation.util.DateTimeUtil.getCurrentDate;

@RestController
@Tag(name = "1 Votes", description = "Votes API")
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractController {
    private static String NO_VOTE_FOUND = "No vote found";
    private static String NO_MENU_FOUND = "No menu found";
    private static String ILLEGAL_VOTING_DATE = "Illegal voting date (in the past)";

    @GetMapping("/votes")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public Vote getVote(@RequestParam @Nullable Date date, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userDetails.getUser();
        log.error("Get vote for user {}", user);
        Optional<Vote> vote = voteRepository.findByDateAndUser(date == null ? getCurrentDate() : date, user);
        if (vote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_VOTE_FOUND);
        }
        return vote.get();
    }

    @PostMapping("/votes")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public void acceptVote(@RequestParam int menuId, @RequestParam @Nullable Date date, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        log.info("User {} voting for the {} menu", userDetails.getUsername(), menuId);
        Date votingDate = date == null ? getCurrentDate() : date;

        Menu menu = menuRepository.findById(menuId).orElse(null);
        if (menu == null || !menu.getDate().equals(votingDate)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_MENU_FOUND);
        }
        if (votingDate.before(getCurrentDate())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ILLEGAL_VOTING_DATE);
        }

        Vote vote = new Vote(date, userDetails.getUser(), menu);
        voteRepository.save(vote);
    }

    @DeleteMapping("/votes")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    public void deleteVote(@RequestParam @Nullable Date date, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        date = date == null ? getCurrentDate() : date;
        User user = userDetails.getUser();
        log.info("User {} deletes vote on {}", userDetails.getUsername(), date.toString());
        Optional<Vote> vote = voteRepository.findByDateAndUser(date, user);
        if (vote.isPresent()) {
            voteRepository.delete(vote.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_VOTE_FOUND);
        }
    }
}
