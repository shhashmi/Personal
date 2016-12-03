package com.gojek.locator.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.gojek")
public class Main {

	
	public static void main(String[] str) {
		SpringApplication.run(Main.class, str);
	}
}
