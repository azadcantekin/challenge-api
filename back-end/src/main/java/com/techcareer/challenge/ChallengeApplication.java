package com.techcareer.challenge;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);

		log.info("Application started");

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			log.info("Application is shutting down");
			// Perform any additional cleanup or logging before the application shuts down
		}));
	}
}


