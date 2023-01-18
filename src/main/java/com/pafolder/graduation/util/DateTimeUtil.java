package com.pafolder.graduation.util;

import java.time.LocalTime;

public class DateTimeUtil {
    public static final LocalTime VOTING_TIME_LIMIT = LocalTime.of(11, 00);
    public static final LocalTime CURRENT_TIME_BEFORE_VOTING_TIME_LIMIT = VOTING_TIME_LIMIT;
    public static final LocalTime CURRENT_TIME_AFTER_VOTING_TIME_LIMIT = VOTING_TIME_LIMIT.plusSeconds(1);
    private static LocalTime currentTimeForTests;

    public static LocalTime getCurrentTime() {
        return currentTimeForTests == null ? LocalTime.now() : currentTimeForTests;
    }

    public static boolean isLateToVote() {
        return getCurrentTime().isAfter(VOTING_TIME_LIMIT);
    }

    public static void setCurrentTimeForTests(LocalTime currentTimeForTests) {
        DateTimeUtil.currentTimeForTests = currentTimeForTests;
    }
}
