package com.pafolder.graduation.service;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.repository.UserRepository;
import com.pafolder.graduation.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    private PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return repository.findAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void updateIsEnabled(int id, boolean isEnabled) {
        repository.updateIsEnabled(id, isEnabled);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Cacheable("users")
    public User getByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    @Cacheable("users")
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
