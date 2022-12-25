package com.pafolder.graduation;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GraduationApplicationTests {
Logger logger = LoggerFactory.getLogger("yellow");
	@Test
	void contextLoads() {
		logger.info("Hello from Test!");
	}

}
