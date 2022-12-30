package com.pafolder.graduation.repository;

import com.pafolder.graduation.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u")
    @EntityGraph(attributePaths = "role")
    List<User> findAll();

    Optional<User> findByEmail(String email);
}
