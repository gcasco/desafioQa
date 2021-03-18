package com.example.desaqa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class FlightReservation extends Reservation{
    private String flightNumber;
    private String origin;
    private String destination;
    @Pattern(regexp = "Economy|Business|ECONOMY|BUSINESS",message = "El tipo de asiento ingresado es inválido")
    private String seatType;
    @Max(value = 150, message = "No es posible realizar una reserva de mas de 150 asientos")
    private Integer seats;

}
