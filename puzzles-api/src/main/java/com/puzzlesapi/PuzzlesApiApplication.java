package com.puzzlesapi;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PuzzlesApiApplication {
	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(PuzzlesApiApplication.class, args);
	}

}
