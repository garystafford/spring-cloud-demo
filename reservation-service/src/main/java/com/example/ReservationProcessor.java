package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import static org.springframework.cloud.stream.messaging.Sink.INPUT;

@MessageEndpoint
class ReservationProcessor {

    @Autowired
    private ReservationRepository reservationRepository;

    @ServiceActivator(inputChannel = INPUT)
    public void acceptNewReservation(String rn) {
        this.reservationRepository.save(new Reservation(rn));
    }
}
