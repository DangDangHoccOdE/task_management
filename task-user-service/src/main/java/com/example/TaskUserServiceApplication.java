package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class TaskUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskUserServiceApplication.class, args);
	}

}
