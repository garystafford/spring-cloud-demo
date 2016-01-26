package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableBinding(Source.class)
@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
public class ReservationClientApplication {

    @Bean
    AlwaysSampler alwaysSampler() {
        return new AlwaysSampler();
    }

    public static void main(String[] args) {
        SpringApplication.run(ReservationClientApplication.class, args);
    }
}

