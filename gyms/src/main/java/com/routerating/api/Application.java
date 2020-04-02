package com.routerating.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.routerating.api", lazyInit = true)
@EnableJpaRepositories(basePackages = "com.routerating.api")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
