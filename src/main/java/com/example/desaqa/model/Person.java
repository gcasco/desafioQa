package com.example.desaqa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Calendar;

@Getter @Setter
public class Person {
    @NotNull(message = "El dni es obligatorio")
    private Long dni;

    @NotNull(message = "El nombre es obligatorio")
    @NotEmpty(message = "El nombre es obligatorio")
    private String name;

    @NotNull(message = "El apellido es obligatorio")
    @NotEmpty(message = "El apellido es obligatorio")
    private String lastname;

    @NotNull(message = "La fecha de Nacimiento es obligatoria")
    @Past(message = "La fecha de Nacimiento debe ser menor a la fecha actual")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Calendar birthDate;

    @NotNull(message = "El email es obligatorio")
    @NotEmpty(message = "El email es obligatorio")
    @Email(message="El mail debe tener un formato de email")
    private String mail;
}
