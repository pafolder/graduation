package com.pafolder.graduation.controller;

import com.pafolder.graduation.security.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Votes", description = "The Graduation Menus API")
@RequestMapping("/rest")
public class VoteRestController extends AbstractRestController {

    @PostMapping("/votes")
    @Operation(security = {@SecurityRequirement(name = "basicScheme")})
    @ResponseStatus(HttpStatus.OK)
    public void acceptVote(int restaurantId, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User {} voting for the {} restaurant", userDetails.getUsername(), restaurantId);
    }
}
