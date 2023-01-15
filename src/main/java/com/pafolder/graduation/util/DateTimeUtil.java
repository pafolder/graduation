package com.pafolder.graduation.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {
    //    public static final Date CURRENT_DATE = Date.valueOf("2022-12-16");
    public static final LocalTime CURRENT_TIME = LocalTime.of(10, 23);
    public static final LocalTime VOTING_TIME_LIMIT = LocalTime.of(11, 00);

    private static LocalTime currentTime = CURRENT_TIME;

    public static LocalDate getCurrentDate() {
        return LocalDateTime.now().toLocalDate();
    }

    public static LocalTime getCurrentTime() {
        return currentTime;
//        return Time.valueOf(LocalDateTime.now().toLocalTime());
    }

    public static void setCurrentTime(LocalTime newCurrentTime) {
        currentTime = newCurrentTime;
    }

    public static LocalDate getNextVotingDate() {
        return getCurrentTime().isAfter(VOTING_TIME_LIMIT) ?
                getCurrentDate().plusDays(1) : getCurrentDate();
    }
}
