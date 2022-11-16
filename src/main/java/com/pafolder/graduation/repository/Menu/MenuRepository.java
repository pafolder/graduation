package com.pafolder.graduation.repository.Menu;

import com.pafolder.graduation.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("SELECT m FROM Menu m")
    @EntityGraph(attributePaths = "menuItems")
    List<Menu> findAll();

    @Query("SELECT m FROM Menu m WHERE m.date = ?1")
    @EntityGraph(attributePaths = "menuItems")
    List<Menu> findByDate(Date date);
}
