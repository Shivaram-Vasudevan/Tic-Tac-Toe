package com.example.trial;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.trial")
public class GameApplicationContext {

	@Bean
	public ComputerPlayer computerPlayer() {
		return new ComputerPlayer();
	}
}
