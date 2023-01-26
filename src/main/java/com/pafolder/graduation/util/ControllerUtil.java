package com.pafolder.graduation.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.pafolder.graduation.util.DateTimeUtil.isLateToVote;

public abstract class ControllerUtil {
    private static final int PRESET_ADMIN_ID = 1;
    public static final String TOO_LATE_TO_VOTE = "It's too late to vote, possible until 11:00 am";
    private static final String CANT_CHANGE_PRESET_ADMIN = "Changing preset Admin isn't allowed in test mode";

    public static void throwExceptionIfLateToVote() throws ResponseStatusException {
        if (isLateToVote()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, TOO_LATE_TO_VOTE);
        }
    }

    public static void protectAdminPreset(int id) {
        if (id == PRESET_ADMIN_ID) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, CANT_CHANGE_PRESET_ADMIN);
        }
    }
}
