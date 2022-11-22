package com.pafolder.graduation;

import com.pafolder.graduation.controller.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Configuration
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		LoggerFactory.getLogger("root").error("Servlet intitalizer");

		return application.sources(ServletInitializer.class);
	}

	public static void main(String[] args) {
		LoggerFactory.getLogger("yellow").error("Servlet!!!");

//		SpringApplication sa = new SpringApplication(ServletInitializer.class);
//		sa.run(args);

		ApplicationContext context = SpringApplication.run(ServletInitializer.class, args);
		Logger logger = LoggerFactory.getLogger("yellow");
		RestController controller = context.getBean(RestController.class);
		controller.testDataBase();
	}
	@Controller
	public static class WarInitializerController {

		@GetMapping("/")
		public String handler() {
// 			return "jsp/index";
 			return "thymeleafPage";
		}
	}
}
