package com.pafolder.graduation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MenuControllerTest extends AbstractControllerTest{
    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/menus").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("petr_p@yandex.com", "password"))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/menus").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/menus").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("petr_p@yandex.com", "password"))
//                .content(JsonUtil.writeValue(updatedTo)))
                        .content("{\"id\":0,\"restaurant\":{\"id\":0,\"name\":\"Старый Ресторан\",\"address\":\"x\"}," +
                                "\"date\":\"2020-01-30\"," +
                                "\"menuItems\":" +
                                "[" +
                                "{\"dishName\":\"Фасоль\",\"dishPrice\":99.99}," +
                                "{\"dishName\":\"Рис\",\"dishPrice\":88.0}" +
                                "]" +
                                "}"))
                .andDo(print())
                .andExpect(status().isCreated());

//        USER_MATCHER.assertMatch(userService.get(USER_ID), UsersUtil.updateFromTo(new User(user), updatedTo));
    }
}