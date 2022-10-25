package ru.bengobro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InternRelexApplication {
	public static void main(String[] args) {
		SpringApplication.run(InternRelexApplication.class, args);
	}
}
