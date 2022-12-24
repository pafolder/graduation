package com.pafolder.graduation;

import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

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
