package com.example.projet_carte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjetCarteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetCarteApplication.class, args);
	}

}
