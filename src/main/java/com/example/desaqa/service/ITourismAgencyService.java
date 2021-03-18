package com.example.desaqa.service;
import com.example.desaqa.exception.GenericException;
import com.example.desaqa.exception.ParamInputInvalidException;
import com.example.desaqa.model.Flight;
import com.example.desaqa.model.FlightReservationDTO;
import com.example.desaqa.model.Hotel;
import com.example.desaqa.model.HotelReservationDTO;
import org.springframework.core.metrics.jfr.FlightRecorderApplicationStartup;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface ITourismAgencyService {
    Hotel getHotel(String hotelCode) throws IOException, ParamInputInvalidException;
    Flight getFlight(String flightNumbre) throws IOException, ParamInputInvalidException;
    List<Hotel> getListHotels() throws IOException;
    List<Flight> getListFlights() throws IOException;
    List<Hotel> getListHotelsByFilters(Date dateFrom, Date dateTo, String destination) throws IOException, GenericException;
    List<Flight> getListFlightsByFilters(Date dateFrom, Date dateTo, String origin, String destination) throws IOException, GenericException;
    Boolean isValidDateRange(Date dateFrom, Date dateTo) throws GenericException;
    Boolean existHotelDestination(String destination) throws IOException, GenericException;
    Boolean existFlightDestination(String destination) throws IOException, GenericException;
    Boolean existFlightOrigin(String origin) throws IOException, GenericException;
    Double calculateInterestForCredictCard(Integer dues);
    Integer getReserveDays(Date dateFrom, Date dateTo);
    Double calculateAmountOfReservation(Double price, Integer daysORseat);
    HotelReservationDTO createReservationHotel(HotelReservationDTO reservationDTO) throws IOException, GenericException;
    FlightReservationDTO createReservationFlight(FlightReservationDTO reservationDTO) throws GenericException, IOException;

}
