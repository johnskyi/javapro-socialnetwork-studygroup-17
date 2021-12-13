package ru.skillbox.socialnetwork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SocialNetworkApplication {
	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkApplication.class, args);
		System.out.println("Hi leather! I am started in 8086 port");
		System.out.println("If you need to look information about my job, look in root project in logs/info directory");
		System.out.println("If you need to look information about my errors, look in root project in logs/error directory");
		System.out.println("Have a nice day!");
		System.out.println("May the force be with you!");
	}

}
