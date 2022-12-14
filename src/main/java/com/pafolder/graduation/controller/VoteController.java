package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.security.UserDetailsImpl;
import com.pafolder.graduation.util.DateTimeUtil;
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
import java.util.List;
import java.util.Optional;

import static com.pafolder.graduation.controller.AbstractController.REST_URL;
import static com.pafolder.graduation.util.DateTimeUtil.*;

@RestController
@Tag(name = "1 Votes", description = "API")
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractController {
    private static final String NO_VOTE_FOUND = "No vote found";
    private static final String NO_MENU_FOUND = "No menu found";
    private static final String TOO_LATE_TO_VOTE = "It's too late to vote (available until 11:00 am)";
    private static final String TOO_LATE_TO_DELETE_VOTE = "It's too late to delete the vote (available until 11:00 am)";

    @GetMapping("/votes")
    @Operation(summary = "Get authenticated user's vote(s)", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "date", description = "Optional: Get user's vote on the specified date. Defaults to all votes for any dates.")
    public List<Vote> getVotes(@RequestParam @Nullable Date date, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        log.info("Get vote for user {}", user);
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
    public void acceptVote(@RequestParam int menuId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        log.info("User {} voting for the {} menu", user.getName(), menuId);
        Menu menu = menuRepository.findById(menuId).orElse(null);
        if (menu == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_MENU_FOUND);
        }
        if (menu.getDate().before(getNextVotingDate())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, TOO_LATE_TO_VOTE + " " +
                    DateTimeUtil.getCurrentDate().toString());
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
    public void deleteVote(@RequestParam @Nullable Date date, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        date = date == null ? getCurrentDate() : date;
        User user = userDetails.getUser();
        log.info("User {} deletes vote on {}", userDetails.getUsername(), date);
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
