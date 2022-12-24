package com.pafolder.graduation.web;

import com.pafolder.graduation.ServletInitializer;
import com.pafolder.graduation.controller.UIController;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//@SpringJUnitWebConfig(locations = {
//        "classpath:spring/spring-app.xml",
//        "classpath:spring/spring-mvc.xml",
//        "classpath:spring/spring-db.xml"
//})
//@ContextConfiguration(classes = WebConfig.class)
@PropertySource({"classpath:db/hsqldb.properties"} )
@SpringJUnitWebConfig(classes = ServletInitializer.class)
@EnableWebSecurity
class MenuUIControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    MockMvc mockMvc;

    @PostConstruct
    private void postConstruct() {
        UIController controller = webApplicationContext.getBean(UIController.class);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }

}