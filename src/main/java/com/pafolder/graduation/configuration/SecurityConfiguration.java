package com.pafolder.graduation.configuration;

import com.pafolder.graduation.service.SPUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
                                                       SPUserDetailsService userDetailsService) throws Exception {
        userDetailsService.setEncoder(passwordEncoder);
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeHttpRequests()
                .requestMatchers( "/login*", "/resources/**", "/error")
                .permitAll()
//                .antMatchers("/", "/login*", "/error", "/search", "/browse", "/recipes/**", "/tags/**",
//                        "/resources/**", "/add", "/create", "/uploadImage", "/edit/**", "/delete/**")
//                .hasRole("ADMIN")
//                .antMatchers("/", "/login*", "/error", "/search", "/browse", "/recipes/**", "/tags/**",
//                        "/resources/**")
//                .hasRole("USER")
//                .and()
//                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
//                .defaultSuccessUrl("/index.html", true)
//                .failureUrl("/login?error=true")
//                .and().logout().permitAll()
//                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}