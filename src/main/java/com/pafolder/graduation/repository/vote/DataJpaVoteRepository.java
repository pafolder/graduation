package com.pafolder.graduation.repository.vote;

import com.pafolder.graduation.model.Vote;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class DataJpaVoteRepository {
    private final VoteRepository voteRepository;

    public DataJpaVoteRepository(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

  public List<Vote> getAll() {
        return voteRepository.findAll();
    }

  public List<Vote> getAllByDate(Date date) {
        return voteRepository.findAllByDate(date);
    }

    public void save(Vote vote) {
        voteRepository.save(vote);
    }
}
