package com.project.gryllo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GrylloApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrylloApplication.class, args);
	}

}
