package com.example.desaqa.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class PaymentMethod {

    @NotNull(message = "El tipo de tarjeta es obligatorio")
    @NotEmpty(message = "El tipo de tarjeta es obligatorio")
    @Pattern(regexp = "CREDIT|DEBIT",message = "El tipo de Tarjeta ingresado es inválido")
    private String type;

    /*
    * Expresion regular para aceptar el numero de tarjeta de ejemplo="\d{4}"
    * se inserta en la expresion regular que acepta los tipos de tarjetas (válidas) asi "|\d{4}|"
    */

    @NotNull(message = "Debes ingresar el codigo de tarjeta")
    @NotEmpty(message = "Debes ingresar el codigo de tarjeta")
    @Pattern(regexp = "^(?:4\\d([\\- ])?\\d{6}\\1\\d{5}|(?:4\\d{3}|\\d{4}|5[1-5]\\d{2}|6011)([\\- ])?\\d{4}\\2\\d{4}\\2\\d{4})$",
            message = "El código de tarjeta ingresado es inválido")
    private String number;
    @NotNull(message = "debe ingresar en cuantos pagos hara la reserva")
    @Min(value = 1, message = "El numero de pagos debe ser por lo menos 1")
    private Integer dues;
}
