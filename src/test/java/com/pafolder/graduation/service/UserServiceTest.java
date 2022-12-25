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
    private UserService service;

    @Test
    void getAll() {
        log.info("Testing User Service ===/s");
        List<User> userList = service.getAll();
        for (User user : userList) {
            log.info(user.toString());
            Assertions.assertEquals(userList, userList);
        }
    }

    @Test
    void save() {
        service.save(updatedUser);
        User userRead = service.getByEmail(updatedUser.getEmail());
        Assertions.assertEquals( userRead, updatedUser);
    }

    @Test
    void create() {
        service.save(newUser);
        User userRead = service.getByEmail(newUser.getEmail());
//        newUser.setId(NEW_USER_ID);
//        Assertions.assertEquals( userRead, newUser);
        USER_MATCHER.assertMatch(userRead, newUser);
    }

    @Test
    void delete() {
        int id = user.getId();
        service.delete(id);
        User us = service.getById(id);
        Assertions.assertThrows(Exception.class, () -> service.getById(id).equals(user));
    }

    @Test
    void getByEmail() {
    }

    @Test
    void getById() {
    }
}