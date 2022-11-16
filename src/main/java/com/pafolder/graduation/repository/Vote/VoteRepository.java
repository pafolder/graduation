package com.pafolder.graduation.repository.Vote;

import com.pafolder.graduation.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    //    @Query("SELECT v FROM Vote v")
    @EntityGraph(attributePaths = {"user", "menu"})
    List<Vote> findAll();
}
