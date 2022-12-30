package com.pafolder.graduation.util;

import java.sql.Date;
import java.sql.Time;

public class DateTimeUtil {
    private static Date CURRENT_DATE = Date.valueOf("2022-12-16");
    private static Time CURRENT_TIME = Time.valueOf("10:23:00");
    private static Time VOTING_TIME_LIMIT = Time.valueOf("11:00:00");

    public static Date getCurrentDate() {
        return CURRENT_DATE;
    }

    public static Time getCurrentTime() {
        return CURRENT_TIME;
    }
}
