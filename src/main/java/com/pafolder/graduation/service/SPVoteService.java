package com.pafolder.graduation.service;

import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.repository.Vote.DataJpaVoteRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SPVoteService {
    DataJpaVoteRepository voteRepository;

    public SPVoteService(DataJpaVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> getAll() {
        return voteRepository.getAll();
    }

    public Vote save(@Valid Vote vote) {
        voteRepository.save(vote);
        return vote;
    }
}
