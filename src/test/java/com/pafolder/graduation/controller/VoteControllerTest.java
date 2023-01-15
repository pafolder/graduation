package com.pafolder.graduation.controller;

import com.pafolder.graduation.model.Vote;
import com.pafolder.graduation.util.DateTimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;
import java.util.Optional;

import static com.pafolder.graduation.TestData.*;
import static com.pafolder.graduation.controller.AbstractController.REST_URL;
import static com.pafolder.graduation.util.DateTimeUtil.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    @Test
    void sendVote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("restaurantId", Integer.toString(RESTAURANT_ID_FOR_FIRST_VOTE)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateVote() throws Exception {
        DateTimeUtil.setCurrentTime(LocalTime.of(10, 23));
        Vote initialVote = voteRepository.findByDateAndUser(getCurrentDate(), user).get();
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("restaurantId", Integer.toString(RESTAURANT_ID_FOR_SECOND_VOTE)))
                .andDo(print())
                .andExpect(status().isCreated());
        Optional<Vote> updatedVote = voteRepository.findByDateAndUser(getCurrentDate(), user);
        Assertions.assertTrue(updatedVote.map(value -> value.getMenu().getId().equals(RESTAURANT_ID_FOR_SECOND_VOTE))
                .orElse(false));
    }

    @Test
    void sendVoteUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                                REST_URL + "/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("restaurantId", "0"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void sendVoteTooLate() throws Exception {
        DateTimeUtil.setCurrentTime(LocalTime.of(11, 00, 01));
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("restaurantId", Integer.toString(RESTAURANT_ID_FOR_FIRST_VOTE)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .toLowerCase()
                .matches(".*it's too late to vote.*".toLowerCase()));
        DateTimeUtil.setCurrentTime(CURRENT_TIME);
    }

    @Test
    void sendVoteForNonexistentRestaurant() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("restaurantId", NONEXISTENT_ID_STRING))
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
        LocalTime currentTime = getCurrentTime();
        setCurrentTime(LocalTime.of(10, 30));
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/vote").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword())))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/vote").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                )
                .andDo(print())
                .andExpect(status().isNotFound());
        setCurrentTime(currentTime);
    }

    @Test
    void deleteVoteTooLate() throws Exception {
        LocalTime currentTime = getCurrentTime();
        setCurrentTime(LocalTime.of(11, 01));
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/vote").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .toLowerCase()
                .matches(".*too late to delete the vote.*".toLowerCase()));
        setCurrentTime(currentTime);
    }
}
