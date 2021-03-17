package com.example.desaqa.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flight {
    @JsonProperty("code")
    @JsonAlias("Nro Vuelo")
    private String code;

    @JsonProperty("origin")
    @JsonAlias("Origen")
    private String origin;

    @JsonProperty("destination")
    @JsonAlias("Destino")
    private String destination;

    @JsonProperty("seatType")
    @JsonAlias("Tipo Asiento")
    private String seatType;

    @JsonProperty("pricePerPerson")
    @JsonAlias("Precio por persona")
    private Double pricePerPerson;

    @JsonProperty("departureDate")
    @JsonAlias("Fecha ida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Calendar departureDate;

    @JsonProperty("returnDate")
    @JsonAlias("Fecha Vuelta")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Calendar returnDate;

}
