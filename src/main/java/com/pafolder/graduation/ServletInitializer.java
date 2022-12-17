package com.pafolder.graduation;

import com.pafolder.graduation.controller.SPController;
import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.service.SPMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@Configuration
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        LoggerFactory.getLogger("root").error("Servlet initializer");
        return application.sources(ServletInitializer.class);
    }

//	@Controller
//	public static class WarInitializerController {
//		@GetMapping("/")
//		public String handler() {
// 			return "jsp/index";
// 			return "thymeleafPage";
//		}
//	}
}
