package com.pafolder.graduation.controller;

import com.pafolder.graduation.repository.RestaurantRepository;
import com.pafolder.graduation.repository.VoteRepository;
import com.pafolder.graduation.validator.MenuToValidator;
import com.pafolder.graduation.validator.UserToValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class AbstractController {
    public static final String REST_URL = "/api";
    protected Logger log = LoggerFactory.getLogger("yellow");


    @Autowired
    protected VoteRepository voteRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;
    @Autowired
    protected MenuToValidator menuToValidator;
    @Autowired
    protected UserToValidator userToValidator;

    @InitBinder("menuTo")
    protected void initBinderMenuTo(WebDataBinder binder) {
        binder.setValidator(menuToValidator);
    }

    @InitBinder("userTo")
    protected void initBinderUserTo(WebDataBinder binder) {
        binder.setValidator(userToValidator);
    }
}
