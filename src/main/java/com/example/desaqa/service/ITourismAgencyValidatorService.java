package com.example.desaqa.service;

import com.example.desaqa.exception.ParamInputInvalidException;
import com.example.desaqa.model.*;

import java.util.Calendar;
import java.util.List;

public interface ITourismAgencyValidatorService {
    Boolean validateHotelReservationRequest(HotelReservationDTO reservation) throws ParamInputInvalidException;
    Boolean validateFlightReservationRequest(FlightReservationDTO reservation) throws ParamInputInvalidException;
    Boolean validatePaymentMethod(PaymentMethod paymentMethod) throws ParamInputInvalidException;
    Boolean validateAmountReservation(List<Person> people, Integer amount) throws ParamInputInvalidException;
    Boolean validateRoomTypeReservation(String roomType, Integer amount) throws ParamInputInvalidException;
    Boolean validateSeatTypeReservation(String seatType, Integer amount) throws ParamInputInvalidException;
    Boolean validateAgeofPeopleReservation(List<Person> people);
    Boolean validateAgeFromBithDatePerson(Calendar bithDate);

}
