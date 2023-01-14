package com.pafolder.graduation.util;

import java.sql.Date;
import java.sql.Time;

public class DateTimeUtil {
    public static final Date CURRENT_DATE = Date.valueOf("2022-12-16");
    public static final Time CURRENT_TIME = Time.valueOf("10:23:00");
    public static final Time VOTING_TIME_LIMIT = Time.valueOf("11:00:00");

    private static Time currentTime = CURRENT_TIME;
    private static Date currentDate = CURRENT_DATE;

    public static Date getCurrentDate() {
        return currentDate;
//        return Date.valueOf(LocalDateTime.now().toLocalDate());
    }

    public static void setCurrentDate(Date currentDate) {
        DateTimeUtil.currentDate = currentDate;
    }

    public static Time getCurrentTime() {
        return currentTime;
//        return Time.valueOf(LocalDateTime.now().toLocalTime());
    }

    public static void setCurrentTime(Time newCurrentTime) {
        currentTime = newCurrentTime;
    }

    public static Date getNextVotingDate() {
        return getCurrentTime().getTime() <= VOTING_TIME_LIMIT.getTime() ? getCurrentDate() :
                Date.valueOf(getCurrentDate().toLocalDate().plusDays(1));
    }
}
