package com.pafolder.graduation.repository.User;

import com.pafolder.graduation.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Repository
@Validated
public class DataJpaUserRepository {
    private final UserRepository userRepository;

    public DataJpaUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void save(@Valid User user) {
        userRepository.save(user);
    }
}
