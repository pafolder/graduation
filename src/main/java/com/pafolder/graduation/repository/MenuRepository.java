package com.pafolder.graduation.repository;

import com.pafolder.graduation.model.Menu;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Cacheable(value = "menus")
    @EntityGraph(attributePaths = {"restaurant", "menuItems"})
    @Query("SELECT m FROM Menu m WHERE m.menuDate = :menuDate")
    List<Menu> findAllByDate(LocalDate menuDate);

    @Cacheable(value = "menus")
    @EntityGraph(attributePaths = {"restaurant", "menuItems"})
    @Query("SELECT m FROM Menu m WHERE m.menuDate = :menuDate AND m.restaurant.id = :restaurantId")
    Optional<Menu> findByDateAndRestaurantId(@Param("menuDate") LocalDate menuDate, @Param("restaurantId") int restaurantId);
}