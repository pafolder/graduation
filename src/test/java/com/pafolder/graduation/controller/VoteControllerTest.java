package com.pafolder.graduation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
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
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/vote").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail(), admin.getPassword()))
                        .param("menuId", "1"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    void acceptVoteWithException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/vote").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                        .param("menuId", UNEXISTING_ID_STRING))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}