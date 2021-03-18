package com.example.desaqa.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hotel {
    @JsonProperty("code")
    @JsonAlias("Código Hotel")
    private String code;

    @JsonProperty("name")
    @JsonAlias("Nombre")
    private String name;

    @JsonProperty("destination")
    @JsonAlias("Lugar/Ciudad")
    private String destination;

    @JsonProperty("roomType")
    @JsonAlias("Tipo de Habitación")
    private String roomType;

    @JsonProperty("pricePerNight")
    @JsonAlias("Precio por noche")
    private Double pricePerNight;

    @JsonProperty("availableFrom")
    @JsonAlias("Disponible Desde")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Calendar availableFrom;

    @JsonProperty("availableTo")
    @JsonAlias("Disponible hasta")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Calendar availableTo;

}
