package com.pafolder.graduation.service;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.repository.User.DataJpaUserRepository;
import com.pafolder.graduation.security.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    DataJpaUserRepository repository;

    @Autowired
    public UserService(DataJpaUserRepository userRepository) {
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

    private PasswordEncoder encoder;

    public PasswordEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No such user " + email);
        }
        return new UserDetails(user, encoder);
    }
}
