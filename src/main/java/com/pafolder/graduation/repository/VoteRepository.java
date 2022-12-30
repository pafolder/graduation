package com.pafolder.graduation.repository;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    //    @Query("SELECT v FROM Vote v")
    @EntityGraph(attributePaths = {"user", "menu", "menu.restaurant"})
    List<Vote> findAll();

    @EntityGraph(attributePaths = {"user", "menu", "menu.restaurant"})
    List<Vote> findAllByDate(Date date);

    @EntityGraph(attributePaths = {"user", "menu", "menu.restaurant"})
    Vote findByDateAndUser(Date date, User user);

//    @Transactional
//    Vote save(Vote vote);
}
