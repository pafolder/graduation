package com.pafolder.graduation.repository.User;

import com.pafolder.graduation.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Repository
public class DataJpaUserRepository {
    private final UserRepository userRepository;

    public DataJpaUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public User get(String email) {
        return userRepository.getByEmail(email);
    }

    public User get(int id) {
        return userRepository.getById(id);
    }

    @Transactional
    public boolean delete(int id) {
        return userRepository.deleteUserById(id) != 0;
    }
}
