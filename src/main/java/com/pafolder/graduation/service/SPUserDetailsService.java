package com.pafolder.graduation.service;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.security.SPUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SPUserDetailsService implements UserDetailsService {
    private SPUserService userService;
    private PasswordEncoder encoder;

    public PasswordEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    public SPUserDetailsService(SPUserService userService) {
        this.userService = userService;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No such user " + email);
        }
        return new SPUserDetails(user, encoder);
    }
}
