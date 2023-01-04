package com.pafolder.graduation.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Configuration
//@SecurityRequirement(name = "basicScheme")
public class WebAppConfiguration implements WebMvcConfigurer {
    private static String HSQLDB_CONFIGURATION = "db/hsqldb.properties";

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
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info().title("Rest API Documentation for Restaurant Voting Application").version(appVersion)
                        .description("Write the <strong>Description</strong> here<br>...<br>The End")
                        .contact(contact).summary("Summary"));
    }
}
