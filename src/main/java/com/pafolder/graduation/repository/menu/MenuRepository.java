package com.pafolder.graduation.repository.menu;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("SELECT m FROM Menu m")
    @EntityGraph(attributePaths = {"restaurant", "menuItems"})
    List<Menu> findAll();

    @Query("SELECT m FROM Menu m WHERE m.date = ?1")
    @EntityGraph(attributePaths = "menuItems")
    List<Menu> findByDate(Date date);

    boolean deleteById(int id);

    Menu save(Menu menu);

    @Query("SELECT m FROM Menu m WHERE m.date = :date AND m.restaurant = :restaurant")
    Menu findByDateAndRestaurant(@Param("date") Date date, @Param("restaurant") Restaurant restaurant);
}

