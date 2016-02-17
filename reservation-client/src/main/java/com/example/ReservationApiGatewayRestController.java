package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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

    public String getReservationServiceMessageFallback() {
        return "Unable to contact Reservation Service";
    }

    @HystrixCommand(fallbackMethod = "getReservationNameFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @RequestMapping(path = "/names", method = RequestMethod.GET)
    public Collection<String> getReservationNames() {

        ParameterizedTypeReference<Resources<Reservation>> ptr =
                new ParameterizedTypeReference<Resources<Reservation>>() {
                };

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

    @HystrixCommand(fallbackMethod = "getReservationServiceMessageFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @RequestMapping(path = "/service-message", method = RequestMethod.GET)
    public String getReservationServiceMessage() {
        return this.restTemplate.getForObject(
                "http://reservation-service/message",
                String.class);
    }
}
