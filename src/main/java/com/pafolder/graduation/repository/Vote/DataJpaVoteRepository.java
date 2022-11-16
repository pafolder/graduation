package com.pafolder.graduation.repository.Vote;

import com.pafolder.graduation.model.Vote;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Repository
@Validated
public class DataJpaVoteRepository {
    private final VoteRepository voteRepository;

    public DataJpaVoteRepository(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

  public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    public void save(@Valid Vote vote) {
        voteRepository.save(vote);
    }
}
