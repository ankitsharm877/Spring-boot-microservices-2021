package com.springboot.basics.springbootin10steps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringbootIn10StepsApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringbootIn10StepsApplication.class, args);
		
		for (String string: context.getBeanDefinitionNames()) {
			System.out.println(string);
		}
	}

}
