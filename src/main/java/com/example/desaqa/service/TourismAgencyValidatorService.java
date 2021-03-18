package com.example.desaqa.service;

import com.example.desaqa.exception.ParamInputInvalidException;
import com.example.desaqa.model.*;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class TourismAgencyValidatorService implements ITourismAgencyValidatorService {


    @Override
    public Boolean validateHotelReservationRequest(HotelReservationDTO reservation) throws ParamInputInvalidException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Boolean valid=false;

        Set<ConstraintViolation<HotelReservationDTO>> violations = validator.validate(reservation);
        for (ConstraintViolation<HotelReservationDTO> violation : violations) {
            String msg=violation.getMessage();
            throw new ParamInputInvalidException(msg);
        }
        if(validateAgeofPeopleReservation(reservation.getBooking().getPeople())
            && validatePaymentMethod(reservation.getBooking().getPaymentMethod())
            && validateAmountReservation(reservation.getBooking().getPeople(),reservation.getBooking().getPeopleAmount())
            && validateRoomTypeReservation(reservation.getBooking().getRoomType(),reservation.getBooking().getPeopleAmount())){
            valid=true;
        }

        return valid;
    }

    @Override
    public Boolean validateFlightReservationRequest(FlightReservationDTO reservation) throws ParamInputInvalidException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Boolean valid=false;

        Set<ConstraintViolation<FlightReservationDTO>> violations = validator.validate(reservation);
        for (ConstraintViolation<FlightReservationDTO> violation : violations) {
            String msg=violation.getMessage();
            throw new ParamInputInvalidException(msg);
        }
        if(validateAgeofPeopleReservation(reservation.getFlightReservation().getPeople())
                && validatePaymentMethod(reservation.getFlightReservation().getPaymentMethod())
                && validateAmountReservation(reservation.getFlightReservation().getPeople(),reservation.getFlightReservation().getSeats())
                && validateSeatTypeReservation(reservation.getFlightReservation().getSeatType(),reservation.getFlightReservation().getSeats())){
            valid=true;
        }

        return valid;
    }

    //Valido que la forma de pago ingresada sea correcta
    @Override
    public Boolean validatePaymentMethod(PaymentMethod paymentMethod) throws ParamInputInvalidException {
        if(paymentMethod.getType().toUpperCase(Locale.ROOT).equals("DEBIT") && paymentMethod.getDues()>1)
        {
            throw new ParamInputInvalidException("No se permite un valor de cuota mayor a 1 cuando la tarjeta es de débito");
        }
        if(!paymentMethod.getType().toUpperCase(Locale.ROOT).equals("DEBIT") && !paymentMethod.getType().toUpperCase(Locale.ROOT).equals("CREDIT")){
            throw new ParamInputInvalidException("Tipo de Tarjeta inválido");
        }
        return true;
    }

    //valido que la cantidad de personas coincida con el numero de personas ingresado
    @Override
    public Boolean validateAmountReservation(List<Person> people, Integer amount) throws ParamInputInvalidException {
        if(!amount.equals(people.size())){
            throw new ParamInputInvalidException("La cantidad de personas ingresada es distinta a las incluidas en people");
        }
        return true;
    }

    //valido que el tipo de habitacion sea para la cantidad de personas ingresada
    @Override
    public Boolean validateRoomTypeReservation(String roomType,Integer amountPeople) throws ParamInputInvalidException {
        roomType=roomType.toUpperCase(Locale.ROOT);
        if(!roomType.equals("DOBLE") &&!roomType.equals("DOUBLE")
              && !roomType.equals("MÚLTIPLE") && !roomType.equals("MULTIPLE")
              && !roomType.equals("TRIPLE")   && !roomType.equals("SINGLE")){
            throw new ParamInputInvalidException("El tipo de habitación es inválido");
        }
        switch (roomType){
            case "SINGLE":
                if(amountPeople>1){
                    throw new ParamInputInvalidException("El tipo de habitación es inválido para la cantidad de personas ingresadas");
                }
                break;
            case "DOUBLE":
            case "DOBLE":
                if(amountPeople>2){
                    throw new ParamInputInvalidException("El tipo de habitación es inválido para la cantidad de personas ingresadas");
                }
                break;
            case "TRIPLE":
                if(amountPeople>3){
                    throw new ParamInputInvalidException("La cantidad max de personas es de 10");
                }
                break;
            case "MULTIPLE":
            case "MÚLTIPLE":
                if(amountPeople>10){
                    throw new ParamInputInvalidException("La cantidad max de personas es de 10");
                }
                break;
        }
        return true;
    }

    //Valido que el tipo de asiento este dentro de los parametros aceptables y que la cantidad de asientos no exceda el maximo
    @Override
    public Boolean validateSeatTypeReservation(String seatType, Integer amount) throws ParamInputInvalidException {
        if(!seatType.toUpperCase(Locale.ROOT).equals("ECONOMY")
                && !seatType.toUpperCase(Locale.ROOT).equals("BUSINESS")){
            throw new ParamInputInvalidException("El tipo de asiento ingresado es inválido");
        }
        if(amount>150){
            throw new ParamInputInvalidException("No es posible realizar una reserva de mas de 150 asientos");
        }
        return true;
    }

    //Valido que al menos una persona ingresada sea mayor de edad
    @Override
    public Boolean validateAgeofPeopleReservation(List<Person> people) {
        Boolean valid = false;
        for (Person person: people) {
            if(validateAgeFromBithDatePerson(person.getBirthDate())){
                valid=true;
            }
        }
        return valid;
    }

    //valido si es mayor de edad
    @Override
    public Boolean validateAgeFromBithDatePerson(Calendar bithDate) {
        LocalDate timeToBirthDate = LocalDate.of(bithDate.getTime().getYear(), bithDate.getTime().getMonth(), bithDate.getTime().getDay());
        LocalDate timeToNow = LocalDate.now();
        Period result = Period.between(timeToBirthDate, timeToNow);
        return result.getYears()>=18;
    }
}
