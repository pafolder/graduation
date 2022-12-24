package com.pafolder.graduation.service;

import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.repository.Vote.DataJpaVoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {
    DataJpaVoteRepository voteRepository;

    public VoteService(DataJpaVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> getAll() {
        return voteRepository.getAll();
    }

    public Vote save(Vote vote) {
        voteRepository.save(vote);
        return vote;
    }
}
