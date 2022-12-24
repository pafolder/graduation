package com.pafolder.graduation.controller;

import com.pafolder.graduation.service.MenuService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Locale;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Sql(scripts = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
@EnableWebSecurity
class MenuRestControllerTest {
    private static final Locale RU_LOCALE = new Locale("ru");
    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private MockMvc mockMvc;

    @Autowired
    private Environment env;

    @Autowired
    private WebApplicationContext webApplicationContext;

//    @Autowired
//    protected MessageSourceAccessor messageSourceAccessor;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
//                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    @Autowired
    MenuService menuService;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("petr_p@yandex.com", "password"))
)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void addMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest").contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("petr_p@yandex.com", "password"))
//                .content(JsonUtil.writeValue(updatedTo)))
                .content("{\"id\":0,\"restaurant\":\"Старый Ресторан\"," +
                        "\"date\":\"2020-01-30\"," +
                        "\"menuItems\":" +
                        "[" +
                        "{\"dishName\":\"Фасоль\",\"dishPrice\":99.99}," +
                        "{\"dishName\":\"Рис\",\"dishPrice\":88.0}" +
                        "]" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());

//        USER_MATCHER.assertMatch(userService.get(USER_ID), UsersUtil.updateFromTo(new User(user), updatedTo));
    }
}