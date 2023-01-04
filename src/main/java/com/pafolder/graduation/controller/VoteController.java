package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.security.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pafolder.graduation.controller.AbstractController.REST_URL;
import static com.pafolder.graduation.util.DateTimeUtil.*;

@RestController
@Tag(name = "1 Votes", description = "API")
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractController {
    private static String NO_VOTE_FOUND = "No vote found";
    private static String NO_MENU_FOUND = "No menu found";
    private static String ILLEGAL_VOTING_DATE = "Illegal voting date (in the past)";
    private static String TOO_LATE_TO_VOTE = "It's too late to vote (available until 11:00 am)";
    private static String TOO_LATE_TO_DELETE_VOTE = "It's too late to delete the vote (available until 11:00 am)";

    @GetMapping("/votes")
    @Operation(summary = "Get authenticated user's vote(s)", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "date", description = "Optional: Get user's vote on the specified date. Defaults to all votes for any dates.")
    public List<Vote> getVotes(@RequestParam @Nullable Date date, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userDetails.getUser();
        log.error("Get vote for user {}", user);
        List<Vote> votes;
        votes = date == null ? voteRepository.findAllByUser(user) :
                voteRepository.findByDateAndUser(date, user).map(List::of).orElse(null);
        if (votes == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_VOTE_FOUND);
        }
        return votes;
    }

    @PostMapping("/votes")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @Operation(summary = "Send authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "menuId", description = "Id of the Menu authenticated user votes for.")
    @Transactional
    public void acceptVote(@RequestParam int menuId, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        User user = userDetails.getUser();
        log.info("User {} voting for the {} menu", user.getName(), menuId);
        Menu menu = menuRepository.findById(menuId).orElse(null);
        if (menu == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_MENU_FOUND);
        }
        if (menu.getDate().before(getNextVotingDate())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, TOO_LATE_TO_VOTE);
        }
        Vote vote = new Vote(userDetails.getUser(), menu);
        voteRepository.findByDateAndUser(menu.getDate(), user)
                .ifPresent(existingUser -> vote.setId(existingUser.getId()));
        voteRepository.save(vote);
    }

    @DeleteMapping("/votes")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "date", description = "Optional: Date to delete vote on. Defaults to the next launch date.")
    @Transactional
    public void deleteVote(@RequestParam @Nullable Date date, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        date = date == null ? getCurrentDate() : date;
        User user = userDetails.getUser();
        log.info("User {} deletes vote on {}", userDetails.getUsername(), date.toString());
        Optional<Vote> vote = voteRepository.findByDateAndUser(date, user);
        if (vote.isPresent()) {
            if (vote.get().getMenu().getDate().before(getNextVotingDate())) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, TOO_LATE_TO_DELETE_VOTE);
            }
            voteRepository.delete(vote.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_VOTE_FOUND);
        }
    }
}
