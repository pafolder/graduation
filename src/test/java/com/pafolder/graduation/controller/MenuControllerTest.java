package com.pafolder.graduation.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.pafolder.graduation.TestData.user;
import static com.pafolder.graduation.util.DateTimeUtil.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuControllerTest extends AbstractControllerTest {

    @Test
    void getAll() throws Exception {
        setCurrentTimeForTests(CURRENT_TIME_BEFORE_VOTING_TIME_LIMIT);
        mockMvc.perform(MockMvcRequestBuilders.get(MenuController.REST_URL).contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllTooLate() throws Exception {
        setCurrentTimeForTests(CURRENT_TIME_AFTER_VOTING_TIME_LIMIT);
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.get(MenuController.REST_URL).contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .matches(".*" + MenuController.VOTING_IS_OVER + ".*"));
    }

    @Test
    void getAllUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(MenuController.REST_URL).contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}