package com.pafolder.graduation.service;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.repository.User.DataJpaUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    DataJpaUserRepository userRepository;

    @Autowired
    public UserService(DataJpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User save(@Valid User user) {
        userRepository.save(user);
        return user;
    }
}
