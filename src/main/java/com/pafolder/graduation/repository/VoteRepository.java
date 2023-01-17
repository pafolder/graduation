package com.pafolder.graduation.repository;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
//    @EntityGraph(attributePaths = {"user", "menu", "menu.restaurant", "menu.menuItems"})
//    @Query("SELECT v FROM Vote v WHERE :user=v.user ORDER BY v.menu.menuDate ASC")
//    List<Vote> findAllByUser(User user);

    //    @EntityGraph(attributePaths = {"user", "menu", "menu.restaurant", "menu.menuItems"})
    @Query("SELECT v FROM Vote v WHERE :date=v.menu.menuDate GROUP BY v.menu.id")
    List<Vote> findAllByDate(LocalDate date);

    @Query("SELECT v FROM Vote v WHERE :user=v.user AND :date=v.menu.menuDate")
    Optional<Vote> findByDateAndUser(LocalDate date, User user);

    @EntityGraph(attributePaths = {"menu", "menu.restaurant", "menu.menuItems"})
    @Query("SELECT v FROM Vote v WHERE :user=v.user AND :date=v.menu.menuDate")
    Optional<Vote> findByDateAndUserWithMenu(LocalDate date, User user);
}
