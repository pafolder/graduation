package com.pafolder.graduation.controller;

import com.pafolder.graduation.controller.admin.AdminMenuController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.pafolder.graduation.TestData.*;
import static com.pafolder.graduation.controller.admin.AdminMenuController.INCORRECT_MENU_DATE;
import static com.pafolder.graduation.controller.admin.AdminMenuController.NO_RESTAURANT_FOUND;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminControllerTest extends AbstractControllerTest {
    private static final String MENU_ID_TO_DELETE_STRING = "8";
    private static final String RESTAURANT_ID_TO_CREATE_STRING = "2";

    @Test
    void createMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(AdminMenuController.REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail(), admin.getPassword()))
                        .content("{\"restaurantId\":2," + "\"date\":\"" +
                                LocalDate.now().plusDays(1).toString() +
                                "\",\"menuItems\":[{\"dishName\":\"Beef\",\"dishPrice\":488.45}," +
                                "{\"dishName\":\"Garnish\",\"dishPrice\":132.80}]}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void createMenuWithValidationErrorExpired() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.post(AdminMenuController.REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail(), admin.getPassword()))
                        .content("{\"restaurantId\":" + RESTAURANT_ID_TO_CREATE_STRING + "," + "\"date\":\"" +
                                LocalDate.now() +
                                "\",\"menuItems\":[{\"dishName\":\"Beef\",\"dishPrice\":488.45}," +
                                "{\"dishName\":\"Garnish\",\"dishPrice\":132.80}]}"))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .matches(".*" + INCORRECT_MENU_DATE + ".*"));
    }

    @Test
    void createMenuWithValidationErrorBadRestaurantDataNotFound() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.post(AdminMenuController.REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail(), admin.getPassword()))
                        .content("{\"restaurantId\":" + NONEXISTENT_ID_STRING + "," + "\"date\":\"" +
                                LocalDate.now().plusDays(1).toString() +
                                "\",\"menuItems\":[{\"dishName\":\"Beef\",\"dishPrice\":488.45}," +
                                "{\"dishName\":\"Garnish\",\"dishPrice\":132.80}]}"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .matches(".*" + NO_RESTAURANT_FOUND + ".*"));
    }

    @Test
    void deleteMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(
                                AdminMenuController.REST_URL + "/" + MENU_ID_TO_DELETE_STRING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail(), admin.getPassword())))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMenuUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(AdminMenuController.REST_URL +
                                "/" + MENU_ID_TO_DELETE_STRING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword())))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteMenuNotFound() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.delete(AdminMenuController.REST_URL +
                                "/" + NONEXISTENT_ID_STRING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail(), admin.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .matches(".*" + INCORRECT_MENU_DATE + ".*"));
    }
}
