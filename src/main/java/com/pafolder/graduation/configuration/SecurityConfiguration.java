package com.pafolder.graduation.configuration;

import com.pafolder.graduation.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
                                                       UserServiceImpl userService) throws Exception {
        userService.setPasswordEncoder(passwordEncoder);
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/api/admin/**")
                .hasRole("ADMIN")
                .requestMatchers("/api/menus/**", "/api/vote/**", "/api/profile/**")
                .access(AuthorizationManagers.anyOf(
                        AuthorityAuthorizationManager.hasRole("ADMIN"),
                        AuthorityAuthorizationManager.hasRole("USER")))
                .requestMatchers("/", "/login/*", "/resources/**", "/webjars/**", "/manager/**", "/reset",
                        "/v3/**", "/swagger-ui/**", "/error", "/api/register", "/api/menus", "/favicon.ico",
                        "/unauthenticated", "/css/*", "/setdatetime", "/reset")
                .permitAll()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                        .permitAll())
                .csrf().disable()
                .logout().permitAll()
                .and()
                .httpBasic()
                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();
        return http.build();
    }

}