package com.pafolder.graduation.controller;

import com.pafolder.graduation.repository.MenuRepository;
import com.pafolder.graduation.repository.UserRepository;
import com.pafolder.graduation.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {
    protected Logger log = LoggerFactory.getLogger("yellow");
    @Autowired
    protected MenuRepository menuRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected VoteRepository voteRepository;
}
