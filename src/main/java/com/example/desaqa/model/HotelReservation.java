package com.example.desaqa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter @Setter
public class HotelReservation extends Reservation{
    private String hotelCode;
    private String destination;
    @Pattern(regexp = "DOBLE|DOUBLE|TRIPLE|SINGLE",message = "El tipo de Habitacion ingresado es inválido")
    private String roomType;
    @NotNull(message = "La cantidad de personas no puede ser nula")
    @Min(value = 1, message = "La cantidad de personas es por lo menos 1")
    @Max(value = 10, message = "No es posible realizar una reserva de mas de 10 personas")
    private Integer peopleAmount;

}
