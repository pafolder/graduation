package com.pafolder.graduation.util;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.to.UserTo;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.pafolder.graduation.controller.AdminController.*;

public class UserUtil {
    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail(), userTo.getPassword(), User.Role.USER);
    }

    public static void protectPresetUserAndAdmin(Integer id) {
        if (id == ADMIN_ID || id == USER_ID) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, CAN_NOT_CHANGE_PRESET_USER_AND_ADMIN);
        }
    }
}
