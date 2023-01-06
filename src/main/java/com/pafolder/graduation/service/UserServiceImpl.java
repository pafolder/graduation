package com.pafolder.graduation.service;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.repository.UserRepository;
import com.pafolder.graduation.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Secured("ROLE_ADMIN")
    public List<User> getAll() {
        return repository.findAll();
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Secured("ROLE_ADMIN")
    public void updateIsEnabled(int id, boolean isEnabled) {
        repository.updateIsEnabled(id, isEnabled);
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

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("No such user " + email.toLowerCase());
        }
        return new UserDetailsImpl(user);
    }
}
