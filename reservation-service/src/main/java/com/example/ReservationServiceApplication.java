package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @RestResource(path = "by-name")
    Collection<Reservation> findByReservationName(@Param("rn") String rn);
}

@EnableBinding(Sink.class)
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

    @Bean
    AlwaysSampler alwaysSampler() {
        return new AlwaysSampler();
    }

    @Bean
    CommandLineRunner runner(ReservationRepository rr) {
        return args -> {
            Arrays.asList("John,Mary,Mike,Doug,Susan,Amit".split(","))
                    .forEach(x -> rr.save(new Reservation(x)));
            rr.findAll().forEach(System.out::println);
        };
    }
}

@MessageEndpoint
class ReservationProcessor {

    @Autowired
    private ReservationRepository reservationRepository;

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void acceptNewReservation(String rn) {
        this.reservationRepository.save(new Reservation(rn));
    }
}

@Entity
class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private String reservationName;

    Reservation() { // why JPA why??
    }

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationName='" + reservationName + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getReservationName() {
        return reservationName;
    }
}
