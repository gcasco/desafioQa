package com.example.desaqa.service;
import com.example.desaqa.exception.GenericException;
import com.example.desaqa.model.Flight;
import com.example.desaqa.model.Hotel;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface ITourismAgencyService {
    List<Hotel> getListHotels() throws IOException;

    List<Flight> getListFlights() throws IOException;

    List<Hotel> getListHotelsByFilters(Date dateFrom, Date dateTo, String destination) throws IOException, GenericException;

    List<Flight> getListFlightsByFilters(Date dateFrom, Date dateTo, String origin, String destination) throws IOException, GenericException;

    Boolean isValidDateRange(Date dateFrom, Date dateTo) throws GenericException;

    Boolean existHotelDestination(String destination) throws IOException, GenericException;

    Boolean existFlightDestination(String destination) throws IOException, GenericException;

    Boolean existFlightOrigin(String origin) throws IOException, GenericException;
    //Hotel getHotelByCode(String code);
}
