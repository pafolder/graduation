package com.pafolder.graduation.service;

import com.pafolder.graduation.MatcherFactory;
import com.pafolder.graduation.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.pafolder.graduation.TestData.*;

class UserServiceTest extends AbstractServiceTest {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "id");

    @Autowired
    private UserServiceImpl userService;

    @Test
    void getAll() {
        log.info("Testing User Service ===/s");
        List<User> userList = userService.getAll();
        for (User user : userList) {
            Assertions.assertEquals(userList, userList);
        }
    }

    @Test
    void create() {
        userService.save(newUser);
        User userRead = userService.getByEmail(newUser.getEmail());
        USER_MATCHER.assertMatch(userRead, newUser);
    }

    @Test
    void delete() {
        int id = user.getId();
        userService.delete(id);
        User us = userService.getById(id).orElse(null);
        Assertions.assertThrows(Exception.class, () -> userService.getById(id).orElse(null).equals(user));
    }
}