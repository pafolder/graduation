package com.pafolder.graduation;

import com.pafolder.graduation.model.User;

import java.sql.Date;
import java.time.LocalDateTime;

public class TestData {
    private static final String DEFAULT_PASSWORD = "password";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin";
    public static Date DATE1 = Date.valueOf(LocalDateTime.now().toLocalDate());
    public static Date DATE2 = Date.valueOf("2022-12-11");
    public static final User user = new User(2, "User", "user@mail.com", DEFAULT_PASSWORD,
            true, User.Role.USER);
    public static final User updatedUser = new User(1, "Novoivanov", "newmail@nm.com", DEFAULT_PASSWORD,
            true, User.Role.ADMIN);
    public static final int NEW_USER_ID = 8;
    public static final User newUser = new User(null, "Новый Пользователь", "new_user@new_mail.new",
            DEFAULT_PASSWORD, true, User.Role.USER);
    public static final User admin = new User(1, "Administrator", "admin@mail.com",
            DEFAULT_ADMIN_PASSWORD, true, User.Role.ADMIN);
    public static final String NONEXISTENT_ID_STRING = "-1";
    public static final Integer RESTAURANT_ID_FOR_FIRST_VOTE = 1;
    public static final Integer RESTAURANT_ID_FOR_SECOND_VOTE = 2;
}

