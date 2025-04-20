package com.samael.springboot.backend.peliculas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringpeliculasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringpeliculasApplication.class, args);
	}

}
