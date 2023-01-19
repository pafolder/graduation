package com.pafolder.graduation;

import com.pafolder.graduation.model.User;

public class TestData {
    private static final String DEFAULT_PASSWORD = "password";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin";
    public static final String NONEXISTENT_ID_STRING = "0";
    public static final Integer RESTAURANT_ID_FOR_FIRST_VOTE = 1;
    public static final Integer RESTAURANT_ID_FOR_SECOND_VOTE = 2;
    public static final User admin = new User(1, "Administrator", "admin@mail.com",
            DEFAULT_ADMIN_PASSWORD, true, User.Role.ADMIN);
    public static final User user = new User(2, "User", "user@mail.com",
            DEFAULT_PASSWORD, true, User.Role.USER);
}

