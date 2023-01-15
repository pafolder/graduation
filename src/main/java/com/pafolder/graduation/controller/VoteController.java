package com.pafolder.graduation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/vote")
    @Operation(summary = "Get authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    public MappingJacksonValue getVote(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("getVote()");
        Optional<Vote> vote = voteRepository.findByDateAndUser(getNextVotingDate(), userDetails.getUser());
        if (vote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_VOTE_FOUND);
        }
        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("voteJsonFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept("id", "menu", "voteDate", "menu.Items"));
        new ObjectMapper().setFilterProvider(filterProvider);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(vote.get());
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @PostMapping("/vote")
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Send authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "restaurantId", description = "Id of the Restaurant authenticated user votes for")
    @Transactional
    public void sendVote(@RequestParam int restaurantId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("sendVote()");
        if (!getCurrentDate().equals(getNextVotingDate())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, TOO_LATE_TO_VOTE);
        }
        Optional<Menu> menu = menuRepository.findByDateAndRestaurantId(getNextVotingDate(), restaurantId);
        if (menu.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_MENU_FOUND);
        }
        Vote vote = new Vote(userDetails.getUser(), menu.get());
        voteRepository.findByDateAndUser(menu.get().getDate(), userDetails.getUser())
                .ifPresent(existingUser -> vote.setId(existingUser.getId()));
        vote.setVoteDate(getCurrentDate());
        voteRepository.save(vote);
    }

    @DeleteMapping("/vote")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    @Transactional
    public void deleteVote(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        log.info("deleteVote");
        Optional<Vote> vote = voteRepository.findByDateAndUser(getCurrentDate(), user);
        if (vote.isPresent()) {
            if (vote.get().getMenu().getDate().isBefore(getNextVotingDate())) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, TOO_LATE_TO_DELETE_VOTE);
            }
            voteRepository.delete(vote.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_VOTE_FOUND);
        }
    }
}
