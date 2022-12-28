package com.pafolder.graduation.repository.vote;

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
}
