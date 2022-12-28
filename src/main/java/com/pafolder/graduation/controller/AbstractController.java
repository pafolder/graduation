package com.pafolder.graduation.controller;

import com.pafolder.graduation.service.MenuService;
import com.pafolder.graduation.service.UserService;
import com.pafolder.graduation.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {
    protected Logger log = LoggerFactory.getLogger("yellow");
    @Autowired
    protected MenuService menuService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected VoteService voteService;
}
