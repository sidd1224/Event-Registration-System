package com.siddh.EventRegistrationSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EventRegistrationSystem {

	public static void main(String[] args) {
		SpringApplication.run(EventRegistrationSystem.class, args);
	}

}
