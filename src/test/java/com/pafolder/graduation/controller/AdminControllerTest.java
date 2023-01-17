package com.pafolder.graduation.controller;

import com.pafolder.graduation.util.DateTimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.pafolder.graduation.TestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminControllerTest extends AbstractControllerTest {
    private static final String MENU_ID_TO_DELETE_STRING = "0";
    private static final String REST_URL = "/api/admin";

    @Test
    void addMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail(), admin.getPassword()))
                        .content("{\"restaurantId\":4," +
                                "\"date\":\"2022-12-18\",\"date\":\"" +
                                DateTimeUtil.getCurrentDate().plusDays(1).toString() + "\"," +
                                "\"menuItems\":[{\"dishName\":\"Фасоль\",\"dishPrice\":99.99}," +
                                "{\"dishName\":\"Рис\",\"dishPrice\":88.0}]}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void addMenuWithValidationErrorExpired() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail(), admin.getPassword()))
                        .content("{\"restaurantId\":4," +
                                "\"date\":\"" + DateTimeUtil.getCurrentDate() + "\",\"date\":\"" +
                                DateTimeUtil.getCurrentDate().plusDays(1).toString() + "\"," +
                                "\"menuItems\":[{\"dishName\":\"Фасоль\",\"dishPrice\":99.99}," +
                                "{\"dishName\":\"Рис\",\"dishPrice\":88.0}]}")).andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .toLowerCase()
                .matches(".*menu.*expired.*"));
    }

    @Test
    void addMenuWithValidationErrorBadRestaurantDataNotFound() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail(), admin.getPassword()))
                        .content("{\"restaurantId\":" + NONEXISTENT_ID_STRING + "," +
                                "\"date\":\"2022-12-18\",\"date\":\"" +
                                DateTimeUtil.getCurrentDate().plusDays(1).toString() + "\"," +
                                "\"menuItems\":[{\"dishName\":\"Фасоль\",\"dishPrice\":99.99}," +
                                "{\"dishName\":\"Рис\",\"dishPrice\":88.0}]}"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .toLowerCase()
                .matches(".*no.*restaurant found.*".toLowerCase()));
    }

    @Test
    void deleteMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/menus" + "/" + MENU_ID_TO_DELETE_STRING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail(), admin.getPassword())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteMenuUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/menus/" + MENU_ID_TO_DELETE_STRING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword())))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteMenuNotFound() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/menus/"
                                + NONEXISTENT_ID_STRING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail(), admin.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .toLowerCase()
                .matches((".*No.*Menu entity with id.*" + NONEXISTENT_ID_STRING + ".*exists.*").toLowerCase()));
    }
}
