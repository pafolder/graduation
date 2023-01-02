package com.pafolder.graduation.repository;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Restaurant;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Cacheable("menus")
    @Query("SELECT m FROM Menu m")
    @EntityGraph(attributePaths = {"restaurant", "menuItems"})
    List<Menu> findAll();

    @Cacheable("menus")
    @Query("SELECT m FROM Menu m WHERE m.date = ?1")
    @EntityGraph(attributePaths = {"restaurant", "menuItems"})
    List<Menu> findAllByDate(Date date);

    @Modifying
    @Transactional
    Menu save(Menu menu);

//    @Modifying
//    @Transactional
//    Menu save(Menu menu);

    @Modifying
    @Transactional
    boolean deleteById(int id);

    @Cacheable("menus")
    @Query("SELECT m FROM Menu m WHERE m.date = :date AND m.restaurant = :restaurant")
    @EntityGraph(attributePaths = {"restaurant", "menuItems"})
    Optional<Menu> findByDateAndRestaurant(@Param("date") Date date, @Param("restaurant") Restaurant restaurant);
}