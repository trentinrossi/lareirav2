package br.com.lareira;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LareiraApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(LareiraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}
