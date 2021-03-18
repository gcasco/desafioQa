package com.example.desaqa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Setter @Getter
public abstract class Reservation {
    @NotNull(message="La Fecha Desde es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date dateFrom;

    @NotNull(message="La Fecha Hasta es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date dateTo;

    @Valid
    @NotNull(message="La lista de personas no puede ser nula")
    @NotEmpty(message="La lista de personas no puede estar vacia")
    private List<Person> people;

    @Valid
    @NotNull(message = "la forma de pago no puede ser nula")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PaymentMethod paymentMethod;
}
