package com.pafolder.graduation.service;

import com.pafolder.graduation.MatcherFactory;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.pafolder.graduation.TestData.*;

class UserServiceTest extends AbstractServiceTest {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "id");

    @Autowired
    private UserRepository userRepository;

    @Test
    void getAll() {
        log.info("Testing User Service ===/s");
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            log.info(user.toString());
            Assertions.assertEquals(userList, userList);
        }
    }

    @Test
    void save() {
        userRepository.save(updatedUser);
        User userRead = userRepository.findByEmail(updatedUser.getEmail()).orElseThrow();
        Assertions.assertEquals(userRead, updatedUser);
    }

    @Test
    void create() {
        userRepository.save(newUser);
        User userRead = userRepository.findByEmail(newUser.getEmail()).orElseThrow();
//        newUser.setId(NEW_USER_ID);
//        Assertions.assertEquals( userRead, newUser);
        USER_MATCHER.assertMatch(userRead, newUser);
    }

    @Test
    void delete() {
        int id = user.getId();
        userRepository.deleteById(id);
        User us = userRepository.findById(id).orElse(null);
        Assertions.assertThrows(Exception.class, () -> userRepository.findById(id).orElse(null).equals(user));
    }

    @Test
    void getByEmail() {
    }

    @Test
    void getById() {
    }
}