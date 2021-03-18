package com.example.desaqa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class HotelReservationDTO {
    @NotNull(message = "El nombre de usuario es obligatorio")
    @NotEmpty(message = "El nombre de usuario es obligatorio")
    @Email(message="El nombre de usuario debe tener un formato de email")
    private String userName;
    private Double amount;
    private Double interest;
    private Double total;


    @Valid
    @NotNull(message = "Los datos de la reserva no pueden ser nulos")
    private HotelReservation booking;

    private StatusCodeDTO statusCode;
}
