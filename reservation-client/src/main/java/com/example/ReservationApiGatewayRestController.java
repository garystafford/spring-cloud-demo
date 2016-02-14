package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.cloud.stream.messaging.Source.OUTPUT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.messaging.support.MessageBuilder.withPayload;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/reservations")
@RefreshScope
class ReservationApiGatewayRestController {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    @Output(OUTPUT)
    private MessageChannel messageChannel;

    @Value("${message}")
    private String message;


    @Description("Post new reservations using Spring Cloud Stream")
    @RequestMapping(method = POST)
    public void acceptNewReservations(@RequestBody Reservation r) {
        Message<String> build = withPayload(r.getReservationName()).build();
        this.messageChannel.send(build);
    }

    public Collection<String> getReservationNameFallback() {
        return emptyList();
    }

    @HystrixCommand(fallbackMethod = "getReservationNameFallback")
    @RequestMapping(path = "/names", method = RequestMethod.GET)
    public Collection<String> getReservationNames() {

        ParameterizedTypeReference<Resources<Reservation>> ptr =
                new ParameterizedTypeReference<Resources<Reservation>>() {
                };

        int pauseAmount = pauseResponse();
        System.out.printf("\nPause Amount: %d%n\n", pauseAmount);
        if (pauseAmount > 500) {
            try {
                throw new InterruptedException();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                wait(pauseAmount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("\nSuccess\n");

        return this.restTemplate.exchange(
                "http://reservation-service/reservations", GET, null, ptr)
                .getBody()
                .getContent()
                .stream()
                .map(Reservation::getReservationName)
                .collect(toList());
    }

    @RequestMapping(path = "/client-message", method = RequestMethod.GET)
    public String getMessage() {
        return this.message;
    }

    @RequestMapping(path = "/service-message", method = RequestMethod.GET)
    public String getReservationServiceMessage() {
        return this.restTemplate.getForObject(
                "http://reservation-service/message",
                String.class);
    }

    private Integer pauseResponse() {
        //Create random number 1 - 1500
        double randNumber = Math.random();
        double d = randNumber * 1500;

        //Type cast double to int
        int randomInt = (int) d + 1;
        return randomInt;
    }
}
