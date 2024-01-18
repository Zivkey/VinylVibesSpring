package com.example.vinyl.vibes;

import com.example.vinyl.vibes.migration.V1__init__data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(V1__init__data v1) {
		return args -> {
			try {
				v1.migrate();
			} catch (Exception e) {
				System.exit(99);
			}
		};
	}

}
