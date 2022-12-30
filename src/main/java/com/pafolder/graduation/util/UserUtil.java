package com.pafolder.graduation.util;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.to.UserTo;

public class UserUtil {
    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail(), userTo.getPassword(), User.Role.USER);
    }
}
