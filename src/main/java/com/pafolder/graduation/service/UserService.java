package com.pafolder.graduation.service;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.repository.UserRepository;
import com.pafolder.graduation.security.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    UserRepository repository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User save(User user) {
        repository.save(user);
        return user;
    }

    public User create(User user) {
        return repository.save(user);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public Optional<User> getById(int id) {
        return repository.findById(id);
    }

    private PasswordEncoder encoder;

    public PasswordEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No such user " + email);
        }
        return new UserDetails(user, encoder);
    }
}
