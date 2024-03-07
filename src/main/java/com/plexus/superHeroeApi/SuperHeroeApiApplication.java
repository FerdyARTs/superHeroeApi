package com.plexus.superHeroeApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SuperHeroeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperHeroeApiApplication.class, args);
	}

}
