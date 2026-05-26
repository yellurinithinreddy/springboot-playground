package com.nithin.mudule1Introduction.IntroToSpring;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntroToSpringApplication implements CommandLineRunner {
	@Autowired
	PaymentService paymentService;

//	@Autowired
	final NotificationService notificationService;

	//Qualifier should be kept before the constructor parameter, it takes the precedence over anything, if we give
	//qualifier and in conditional we dont give that value that bean will not be created and it throws bean not found
    public IntroToSpringApplication(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public static void main(String[] args) {
		SpringApplication.run(IntroToSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		paymentService.pay();
		notificationService.send("Hello world");
	}

	@PostConstruct
	void beforeInit(){
		System.out.println("Before Paying..");
	}

	@PreDestroy
	void afterInit(){
		System.out.println("After Paying");
	}
}
