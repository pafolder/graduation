package com.pafolder.graduation.service;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.repository.User.DataJpaUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SPUserService {
    DataJpaUserRepository repository;

    @Autowired
    public SPUserService(DataJpaUserRepository userRepository) {
        this.repository = userRepository;
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public User save(User user) {
        repository.save(user);
        return user;
    }

    public User create(User user) {
        return repository.save(user);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public User getByEmail(String email) {
        return repository.get(email);
    }

    public User getById(int id) {
        return repository.get(id);
    }
}
