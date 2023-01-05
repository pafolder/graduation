package com.pafolder.graduation.util;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeUtil {
    public static final Date CURRENT_DATE = Date.valueOf("2022-12-16");
    public static final Time CURRENT_TIME = Time.valueOf("10:23:00");
    public static long VOTING_TIME_LIMIT = Time.valueOf("11:00:00").getTime();

    private static Time currentTime = CURRENT_TIME;

    public static Date getCurrentDate() {
        return CURRENT_DATE;
//        return Date.valueOf(LocalDateTime.now().toLocalDate());
    }

    public static Time getCurrentTime() {
        return currentTime;
//        return Time.valueOf(LocalDateTime.now().toLocalTime());
    }

    public static void setCurrentTime(Time newCurrentTime) {
        currentTime = newCurrentTime;
    }

    public static Date getNextVotingDate() {
        return getCurrentTime().getTime() <= VOTING_TIME_LIMIT ? getCurrentDate() :
                Date.valueOf(getCurrentDate().toLocalDate().plusDays(1));
    }
}
