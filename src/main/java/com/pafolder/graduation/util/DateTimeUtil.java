package com.pafolder.graduation.util;

import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateTimeUtil {
    private static final Date CURRENT_DATE = Date.valueOf("2022-12-16");
    public static final Time CURRENT_TIME = Time.valueOf("10:23:00");
    private static final Time VOTING_TIME_LIMIT = Time.valueOf("11:00:00");

    private static Time currentTime = CURRENT_TIME;

    public static @NotNull Date getCurrentDate() {
        return CURRENT_DATE;
//        return Date.valueOf(LocalDateTime.now().toLocalDate());
    }

    public static Time getCurrentTime() {
        return currentTime;
//        return Time.valueOf(LocalDateTime.now().toLocalTime());
    }

    public static Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static void setCurrentTime(Time newCurrentTime) {
        currentTime = newCurrentTime;
    }

    public static @NotNull Date getNextVotingDate() {
        return getCurrentTime().getTime() <= VOTING_TIME_LIMIT.getTime() ? getCurrentDate() :
                Date.valueOf(getCurrentDate().toLocalDate().plusDays(1));
    }
}
