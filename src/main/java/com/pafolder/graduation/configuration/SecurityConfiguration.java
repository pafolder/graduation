package com.pafolder.graduation.configuration;

import com.pafolder.graduation.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
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
                                                       UserService userService) throws Exception {
        userService.setEncoder(passwordEncoder);
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();
        http.csrf().ignoringRequestMatchers("/login");

        http.authorizeHttpRequests()
                .requestMatchers( "/api/admin/**")
                .hasRole("ADMIN")
                .requestMatchers(  "/api/profile/*")
                .hasRole("USER")
                .requestMatchers(  "/api/menus", "/api/votes")
                .access(AuthorizationManagers.anyOf(
                        AuthorityAuthorizationManager.hasRole("ADMIN"),
                        AuthorityAuthorizationManager.hasRole("USER")))
                .requestMatchers("/", "/login/*", "/resources/**", "/webjars/**",
                        "/v3/**","/swagger-ui/**","/error")
                .permitAll()
//                .and()
//                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
//                        .loginProcessingUrl("/spring_security_check")
                        .defaultSuccessUrl("http://localhost:8080/swagger-ui/index.html", true)
                        .permitAll())
                .csrf().disable()
//                .logout()
//                .and()
//                .failureUrl("/login?error=true")
                .logout().permitAll()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}