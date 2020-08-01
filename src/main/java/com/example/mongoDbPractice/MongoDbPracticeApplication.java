package com.example.mongoDbPractice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
public class MongoDbPracticeApplication {

	private static final Logger logger = LoggerFactory.getLogger(MongoDbPracticeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MongoDbPracticeApplication.class, args);

		logger.info("Application is starting");

//		SpringApplication application = new SpringApplication(MongoDbPracticeApplication.class);
//		Properties properties = new Properties();
//		properties.setProperty("spring.main.banner-mode", "log");
//		properties.setProperty("logging.file","/home/rishabh.kohli/Documents/fitnessAPp/logs/main_fitIndia.log");
//		properties.setProperty("logging.pattern.console", "");
//		application.setDefaultProperties(properties);
//		application.run(args);


	}

}
