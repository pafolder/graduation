package com.pafolder.graduation.configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Collections;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {
    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        Contact contact = new Contact();
        contact.name("Sergei Pastukhov");
        contact.email("pafolder@gmail.com");
        contact.url("http://pafolder.com");
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info().title("REST API Documentation for Restaurant Voting Application").version(appVersion)
                        .description(
                                "<b>Restaurant Voting Application (RVA)</b> implements a Voting System for customers " +
                                        "making their decisions which restaurant to have lunch at.<br>" +
                                        "There are two types of users: Admins and regular Users (Customers)." +
                                        "Admins can input Restaurants and theirs lunch Menus of the day " +
                                        "(dish names with prices).<br>" +
                                        "The Menus can be provided in advance for any further date." +
                                        "Users (Customers) can vote for a restaurant they want to have lunch at " +
                                        "today (or any other day<br>provided that Admin has" +
                                        "already input the menu). Only one vote per user per date counts." +
                                        "If the User votes again the same day, the vote will be overridden<br>unless" +
                                        " it was sent after 11:00." +
                                        "In this case, the existing vote cannot be changed." +
                                        "<br><br><b>Credentials for testing:</b>" +
                                        "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                                        "Admin: <i>admin@mail.com / admin</i>" +
                                        "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                                        "User: &nbsp;&nbsp;&nbsp;<i>user@mail.com / password</i>"
                        )
                        .contact(contact));
    }

    @Bean
    public SimpleUrlHandlerMapping customFaviconHandlerMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MIN_VALUE);
        mapping.setUrlMap(Collections.singletonMap(
                "/static/favicon.ico", faviconRequestHandler()));
        return mapping;
    }

    @Bean
    protected ResourceHttpRequestHandler faviconRequestHandler() {
        ResourceHttpRequestHandler requestHandler
                = new ResourceHttpRequestHandler();
        requestHandler.setLocations(Collections.singletonList(new ClassPathResource("/")));
        return requestHandler;
    }
}
