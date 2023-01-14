package com.pafolder.graduation.repository;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @EntityGraph(attributePaths = {"user", "menu", "menu.restaurant", "menu.menuItems"})
    @Query("SELECT v FROM Vote v WHERE :user=v.user ORDER BY v.menu.date ASC")
    List<Vote> findAllByUser(User user);

    @EntityGraph(attributePaths = {"user", "menu", "menu.restaurant", "menu.menuItems"})
    @Query("SELECT v FROM Vote v WHERE :date=v.menu.date GROUP BY v.menu.id")
    List<Vote> findAllByDate(Date date);

    @EntityGraph(attributePaths = {"user", "menu", "menu.restaurant", "menu.menuItems"})
    @Query("SELECT DISTINCT v FROM Vote v WHERE :user=v.user AND :date=v.menu.date")
    Optional<Vote> findByDateAndUser(Date date, User user);

    @EntityGraph(attributePaths = {"user", "menu", "menu.restaurant", "menu.menuItems"})
    List<Vote> findAllByMenu(Menu menu);
}
