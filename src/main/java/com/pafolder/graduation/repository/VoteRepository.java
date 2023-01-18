package com.pafolder.graduation.repository;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("SELECT v FROM Vote v WHERE :user=v.user AND :date=v.menu.menuDate")
    Optional<Vote> findByDateAndUser(LocalDate date, User user);

    @EntityGraph(attributePaths = {"menu", "menu.restaurant", "menu.menuItems"})
    @Query("SELECT v FROM Vote v WHERE :user=v.user AND :date=v.menu.menuDate")
    Optional<Vote> findByDateAndUserWithMenu(LocalDate date, User user);

    @Query("SELECT count(*) FROM Vote v WHERE :date=v.voteDate AND v.menu.restaurant.id=:id")
    int countByVoteDateAndRestaurantId(LocalDate date, int id);
}
