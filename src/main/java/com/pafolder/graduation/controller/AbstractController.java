package com.pafolder.graduation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.pafolder.graduation.util.DateTimeUtil.getCurrentDate;
import static com.pafolder.graduation.util.DateTimeUtil.getNextVotingDate;

public abstract class AbstractController {
    protected Logger log = LoggerFactory.getLogger("yellow");
    private static final int PRESET_ADMIN_ID = 1;
    private static final String TOO_LATE_TO_VOTE = "It's too late to vote (available until 11:00 am)";
    public static final String CANT_CHANGE_PRESET_ADMIN = "Changing preset Admin isn't allowed in test mode";

    protected void throwExceptionIfLateToVote() throws ResponseStatusException {
        if (!getCurrentDate().equals(getNextVotingDate())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, TOO_LATE_TO_VOTE);
        }
    }

    protected void protectPresetAdmin(int id) {
        if (id == PRESET_ADMIN_ID) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, CANT_CHANGE_PRESET_ADMIN);
        }
    }
}
