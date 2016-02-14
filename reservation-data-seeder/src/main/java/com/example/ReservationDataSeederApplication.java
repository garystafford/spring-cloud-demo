package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;

import java.util.Arrays;

@SpringBootApplication
public class ReservationDataSeederApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationDataSeederApplication.class, args);
	}
	@Bean
	@Description("Seed H2 database with records")
	CommandLineRunner runner(ReservationRepository rr) {
		return args -> {
			Arrays.asList("John,Mary,Mike,Doug,Susan,Amit".split(","))
					.forEach(x -> rr.save(new Reservation(x)));
			rr.findAll().forEach(System.out::println);
		};
	}
}
