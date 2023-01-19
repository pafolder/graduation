package com.pafolder.graduation.repository;

import com.pafolder.graduation.model.User;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    @Cacheable("users")
    @Query("SELECT u FROM User u")
    @EntityGraph(attributePaths = "role")
    List<User> findAll();

    @Cacheable("users")
    Optional<User> findByEmail(String email);

    @Transactional
    @Query("UPDATE User u SET u.enabled=:isEnabled WHERE u.id=:id")
    @Modifying
    void updateIsEnabled(@Parameter int id, @Parameter boolean isEnabled);
}
