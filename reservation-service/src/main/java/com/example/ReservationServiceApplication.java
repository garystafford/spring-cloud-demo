package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;

@EnableBinding(Sink.class)
@EnableEurekaClient
@SpringBootApplication
public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

    @Bean
    @Description("Used with Spring Cloud Sleuth/Zipkin")
    AlwaysSampler alwaysSampler() {
        return new AlwaysSampler();
    }

//    @Bean
//    @Description("Seed H2 database with records")
//    CommandLineRunner runner(ReservationRepository rr) {
//        return args -> {
//            Arrays.asList("John,Mary,Mike,Doug,Susan,Amit".split(","))
//                    .forEach(x -> rr.save(new Reservation(x)));
//            rr.findAll().forEach(System.out::println);
//        };
//    }
}

