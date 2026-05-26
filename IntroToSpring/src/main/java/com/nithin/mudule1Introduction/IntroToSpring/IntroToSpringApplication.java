package com.nithin.mudule1Introduction.IntroToSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntroToSpringApplication implements CommandLineRunner {
	@Autowired
	PaymentService paymentService;

	public static void main(String[] args) {
		SpringApplication.run(IntroToSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		paymentService.pay();
	}
}
