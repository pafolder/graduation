package com.pafolder.graduation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.repository.MenuRepository;
import com.pafolder.graduation.repository.VoteRepository;
import com.pafolder.graduation.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.time.LocalDate;

import static com.pafolder.graduation.util.ControllerUtil.throwExceptionIfLateToVote;

@RestController
@AllArgsConstructor
@Tag(name = "1 vote-controller")
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    public static final String REST_URL = "/api/profile/vote";
    static final String NO_VOTE_FOUND = "No vote found";
    static final String VOTE_ALREADY_EXISTS = "Vote already exists";
    public static final String NO_MENU_RESTAURANT_FOUND = "No menu found for restaurant";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private MenuRepository menuRepository;
    private VoteRepository voteRepository;

    @GetMapping
    @Operation(summary = "Get authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    public MappingJacksonValue get(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("get()");
        Vote vote = voteRepository.findByDateAndUserWithMenu(LocalDate.now(), userDetails.getUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_VOTE_FOUND)
                );
        return getFilteredVoteJson(vote);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Send authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "restaurantId", description = "Restaurant Id authenticated user votes for")
    @Transactional
    public ResponseEntity<MappingJacksonValue> create(@RequestParam int restaurantId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("create()");
        throwExceptionIfLateToVote();
        Menu menu = menuRepository.findByDateAndRestaurantId(LocalDate.now(), restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        NO_MENU_RESTAURANT_FOUND));
        voteRepository.findByDateAndUser(LocalDate.now(), userDetails.getUser())
                .ifPresent(vote -> {
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, VOTE_ALREADY_EXISTS);
                });
        Vote createdVote = voteRepository.save(new Vote(null, userDetails.getUser(), LocalDate.now(), menu));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(createdVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(getFilteredVoteJson(createdVote));
    }

    private MappingJacksonValue getFilteredVoteJson(Vote vote) {
        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("voteJsonFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept("id", "menu", "menu.Items"));
        new ObjectMapper().setFilterProvider(filterProvider);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(vote);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Change authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    @Parameter(name = "restaurantId", description = "Restaurant Id authenticated user updates vote for")
    @Transactional
    public void update(@RequestParam int restaurantId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("update()");
        throwExceptionIfLateToVote();
        Vote vote = voteRepository.findByDateAndUser(LocalDate.now(), userDetails.getUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_VOTE_FOUND));
        Menu menu = menuRepository.findByDateAndRestaurantId(LocalDate.now(), restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        NO_MENU_RESTAURANT_FOUND));
        vote.setMenu(menu);
        voteRepository.save(vote);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete authenticated user's vote", security = {@SecurityRequirement(name = "basicScheme")})
    @Transactional
    public void delete(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("delete");
        throwExceptionIfLateToVote();
        Vote vote = voteRepository.findByDateAndUser(LocalDate.now(), userDetails.getUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, NO_VOTE_FOUND));
        voteRepository.delete(vote);
    }
}
