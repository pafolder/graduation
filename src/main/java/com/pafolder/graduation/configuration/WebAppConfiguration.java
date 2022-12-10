package com.pafolder.graduation.configuration;

import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@PropertySource({"classpath:db/hsqldb.properties"})
//@EnableWebMvc
//@Configuration
@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class WebAppConfiguration implements WebMvcConfigurer {
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.pafolder.graduation.model");

        Properties jpaProperties = new Properties();

        jpaProperties.put("database.username", env.getProperty("database.username"));
        jpaProperties.put("database.password", env.getProperty("database.password"));
//        jpaProperties.put("database.url", env.getProperty("database.url"));
        jpaProperties.put("database.init", env.getProperty("database.init"));
        jpaProperties.put("jdbc.initLocation", env.getProperty("jdbc.initLocation"));
        jpaProperties.put("jpa.showSql", env.getProperty("jpa.showSql"));
        jpaProperties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        jpaProperties.put("hibernate.use_sql_comments", env.getProperty("hibernate.use_sql_comments"));

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


    //    @Bean
//    public ViewResolver internalResourceViewResolver() {
//        InternalResourceViewResolver bean = new InternalResourceViewResolver();
//        bean.setViewClass(JstlView.class);
//        bean.setPrefix("/WEB-INF/jsp/");
//        bean.setSuffix(".jsp");
//        LoggerFactory.getLogger("root").error("viewResolver Bean has been created");
//        return bean;
//    }
    @Bean
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewNames("jsp/*");
        viewResolver.setOrder(1);
        LoggerFactory.getLogger("root").info("viewResolver Bean has been created");
        return viewResolver;
    }
}
