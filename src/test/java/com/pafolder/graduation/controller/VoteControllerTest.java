package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.util.DateTimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Time;
import java.util.Optional;

import static com.pafolder.graduation.TestData.*;
import static com.pafolder.graduation.controller.AbstractController.REST_URL;
import static com.pafolder.graduation.util.DateTimeUtil.CURRENT_TIME;
import static com.pafolder.graduation.util.DateTimeUtil.getCurrentDate;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    @Test
    void sendVote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("menuId", Integer.toString(MENU_ID_FOR_FIRST_VOTE)))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    void updateVote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("menuId", Integer.toString(MENU_ID_FOR_SECOND_VOTE)))
                .andDo(print())
                .andExpect(status().isAccepted());
        Optional<Vote> vote = voteRepository.findByDateAndUser(getCurrentDate(), user);
        Assertions.assertTrue(vote.map(value -> value.getMenu().getId().equals(MENU_ID_FOR_SECOND_VOTE)).orElse(false));
    }

    @Test
    void sendVoteUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                                REST_URL + "/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("menuId", "0"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void sendVoteTooLate() throws Exception {
        DateTimeUtil.setCurrentTime(Time.valueOf("11:00:01"));
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("menuId", Integer.toString(MENU_ID_FOR_FIRST_VOTE)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .toLowerCase()
                .matches(".*It's too late to vote.*".toLowerCase()));
        DateTimeUtil.setCurrentTime(CURRENT_TIME);
    }

    @Test
    void sendVoteForNonexistentMenu() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("menuId", NONEXISTENT_ID_STRING))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .toLowerCase()
                .matches(".*no.*menu.*found.*".toLowerCase()));
    }

    @Test
    void deleteVote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/votes").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("date", DATE1.toString()))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/votes").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("date", DATE1.toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNonexistentVote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/votes").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("date", DATE2.toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
