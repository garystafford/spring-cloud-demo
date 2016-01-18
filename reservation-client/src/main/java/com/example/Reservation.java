package com.example;

// Client-side DTO
class Reservation {
    private Long Id;
    private String reservationName;

    public Long getId() {
        return Id;
    }

    public String getReservationName() {
        return this.reservationName;
    }
}
