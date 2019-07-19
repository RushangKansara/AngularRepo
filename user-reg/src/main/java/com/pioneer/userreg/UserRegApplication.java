package com.pioneer.userreg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:messages/messages.properties")
public class UserRegApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserRegApplication.class, args);
	}

}
