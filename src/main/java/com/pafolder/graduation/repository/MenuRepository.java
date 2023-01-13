package com.pafolder.graduation.repository;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Restaurant;
import jakarta.validation.constraints.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Cacheable("menus")
    @EntityGraph(attributePaths = {"restaurant", "menuItems"})
    @Query("SELECT m FROM Menu m")
    List<Menu> findAll();

    @Cacheable("menus")
    @EntityGraph(attributePaths = {"restaurant", "menuItems"})
    @Query("SELECT m FROM Menu m WHERE m.date = ?1")
    List<Menu> findAllByDate(@NotNull Date date);

    @Transactional
    Menu save(Menu menu);

    @Transactional
    boolean deleteById(int id);

    @Cacheable("menus")
    @EntityGraph(attributePaths = {"restaurant", "menuItems"})
    @Query("SELECT m FROM Menu m WHERE m.date = :date AND m.restaurant = :restaurant")
    Optional<Menu> findByDateAndRestaurant(@Param("date") @NotNull Date date, @Param("restaurant") Restaurant restaurant);
}