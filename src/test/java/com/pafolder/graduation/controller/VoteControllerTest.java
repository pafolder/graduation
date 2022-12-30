package com.pafolder.graduation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.pafolder.graduation.TestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    @Test
    void getAllByDate() {
    }

    @Test
    void acceptVote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/votes").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("menuId", "0"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    void acceptVoteWithException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/votes").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("menuId", NONEXISTING_ID_STRING))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteVote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/votes").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("date", DATE6.toString()))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/votes").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("date", DATE6.toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

        @Test
    void deleteVoteNonExists() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.delete("/rest/votes").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("date", DATE1.toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}