package com.pafolder.graduation;

import com.pafolder.graduation.controller.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainApplication.class, args);
        Logger logger = LoggerFactory.getLogger("yellow");
        RestController controller = context.getBean(RestController.class);
        controller.testDataBase();
    }
}
