package com.pafolder.graduation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

public class MainApplication {

    public static void main(String[] args) {
        LoggerFactory.getLogger("root").error("Main Application???");

        ApplicationContext context = SpringApplication.run(MainApplication.class, args);
//        Logger logger = LoggerFactory.getLogger("yellow");
//        RestController controller = context.getBean(RestController.class);
//        controller.testDataBase();
    }
}
