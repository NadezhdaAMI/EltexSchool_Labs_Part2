package ru.mitina.laba7;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class Laba7Application {

	private static Logger logger = LogManager.getLogger(Laba7Application.class);

	public static void main(String[] args) {

		SpringApplication.run(Laba7Application.class, args);
	}

	public void performSomeTask() {
		logger.info("Starting Spring Boot application!");
		logger.debug("This is a debug message");
		logger.info("This is an info message");
		logger.warn("This is a warn message");
		logger.error("This is an error message");
		logger.fatal("This is a fatal message");
	}
}
