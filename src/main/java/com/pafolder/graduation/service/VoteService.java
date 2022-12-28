package com.pafolder.graduation.service;

import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.repository.vote.DataJpaVoteRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
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

    public List<Vote> getAllByDate(Date date) {
        return voteRepository.getAllByDate(date);
    }

    public Vote add(Vote vote) {
        voteRepository.save(vote);
        return vote;
    }
}
