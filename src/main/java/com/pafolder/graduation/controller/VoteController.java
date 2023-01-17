package com.pafolder.graduation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.repository.MenuRepository;
import com.pafolder.graduation.repository.VoteRepository;
import com.pafolder.graduation.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static com.pafolder.graduation.util.DateTimeUtil.*;

@RestController
@AllArgsConstructor
@Tag(name = "1 vote-controller")
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractController {
    static final String REST_URL = "/api/profile/vote";
    private static final String NO_VOTE_FOUND = "No vote found";
    private static final String VOTE_ALREADY_EXISTS = "Vote already exists";
    private static final String NO_MENU_FOUND = "No menu/restaurant found";

    MenuRepository menuRepository;
    VoteRepository voteRepository;

    @GetMapping
    @Operation(summary = "Get authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    public MappingJacksonValue getVote(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("getVote()");
        Optional<Vote> vote = voteRepository.findByDateAndUserWithMenu(getNextVotingDate(), userDetails.getUser());
        if (vote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_VOTE_FOUND);
        }
        return getVoteJson(vote.get());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Send authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "restaurantId", description = "Id of the Restaurant authenticated user votes for")
    @Transactional
    public ResponseEntity<MappingJacksonValue> createVote(@RequestParam int restaurantId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("sendVote()");
        throwExceptionIfLateToVote();
        Optional<Menu> menu = menuRepository.findByDateAndRestaurantId(getNextVotingDate(), restaurantId);
        if (menu.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_MENU_FOUND);
        }
        if (voteRepository.findByDateAndUser(menu.get().getMenuDate(), userDetails.getUser()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, VOTE_ALREADY_EXISTS);
        }
        Vote vote = new Vote(null, userDetails.getUser(), menu.get(), getCurrentDate());
        voteRepository.findByDateAndUserWithMenu(menu.get().getMenuDate(), userDetails.getUser())
                .ifPresent(existingUser -> vote.setId(existingUser.getId()));
        vote.setVoteDate(getCurrentDate());
        Vote created = voteRepository.save(vote);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(vote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(getVoteJson(created));
    }

    private MappingJacksonValue getVoteJson(Vote vote) {
        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("voteJsonFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept("id", "menu", "voteDate", "menu.Items"));
        new ObjectMapper().setFilterProvider(filterProvider);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(vote);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Change authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "restaurantId", description = "Id of the Restaurant authenticated user updates vote for")
    @Transactional
    public void updateVote(@RequestParam int restaurantId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("updateVote()");
        throwExceptionIfLateToVote();
        Vote vote = voteRepository.findByDateAndUser(getCurrentDate(), userDetails.getUser()).orElse(null);
        if (vote == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_VOTE_FOUND);
        }
        Menu menu = menuRepository.findByDateAndRestaurantId(getNextVotingDate(), restaurantId).orElse(null);
        if (menu == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_MENU_FOUND);
        }
        vote.setMenu(menu);
        voteRepository.save(vote);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    @Transactional
    public void deleteVote(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("deleteVote");
        throwExceptionIfLateToVote();
        User user = userDetails.getUser();
        Optional<Vote> vote = voteRepository.findByDateAndUser(getCurrentDate(), user);
        if (vote.isPresent()) {
            voteRepository.delete(vote.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_VOTE_FOUND);
        }
    }
}
