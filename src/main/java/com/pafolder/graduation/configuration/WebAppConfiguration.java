package com.pafolder.graduation.configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

@Configuration
public class WebAppConfiguration implements WebMvcConfigurer {
    private static final String HSQLDB_CONFIGURATION = "db/hsqldb.properties";

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env, ApplicationContext context) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.pafolder.graduation.model");

        Properties jpaProperties = new Properties();
        try {
            jpaProperties.load(context.getClassLoader().getResourceAsStream(HSQLDB_CONFIGURATION));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }

    @Bean
    public DataSource getDataSource(Environment env) {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        return databaseBuilder.setType(EmbeddedDatabaseType.HSQL)
                .addScript(Objects.requireNonNull(env.getProperty("jdbc.initLocation")))
                .addScript(Objects.requireNonNull(env.getProperty("jdbc.populateLocation"))).build();
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        Contact contact = new Contact();
        contact.name("Sergey Pastukhov");
        contact.email("pafolder@gmail.com");
        contact.url("http://pafolder.com");
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info().title("REST API Documentation for Restaurant Voting Application").version(appVersion)
                        .description(
                                "<b>Restaurant Voting Application (RVA)</b> implements a Voting System for customers " +
                                        "making their decisions which restaurant to have lunch at.<br>\n" +
                                        "There are two types of users: Admins and regular Users (Customers).\n" +
                                        "Admins can input Restaurants and theirs lunch Menus of the day " +
                                        "(dish names with prices).<br>\n" +
                                        "The Menus can be provided in advance for any further date.\n" +
                                        "Users (Customers) can vote for a restaurant they want to have lunch at " +
                                        "today (or any other day<br>provided that Admin has\n" +
                                        "already input the menu). Only one vote per user per date counts.\n" +
                                        "If the User votes again the same day, the vote will be overridden<br>unless" +
                                        " it was sent after 11:00.\n" +
                                        "In this case, the existing vote cannot be changed."
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
