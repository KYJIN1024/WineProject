package com.wine.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.wine.demo.controller","com.wine.demo.model","com.wine.demo.repository","com.wine.demo.service","com.wine.demo.config","com.wine.demo.exception"})
@EnableJpaRepositories(basePackages = "com.wine.demo.repository")
@EntityScan(basePackages = "com.wine.demo.entity")
public class WineprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WineprojectApplication.class, args);
	}

}
