package com.example.desaqa.repository;
import com.example.desaqa.model.Flight;
import com.example.desaqa.model.Hotel;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface ITourismAgencyRepository {
    List<Hotel> getAllHotels() throws IOException;

    List<Flight> getAllFlights() throws IOException;

    List<Hotel> getAllHotelsByFilter(Date dateFrom, Date dateTo, String destination) throws IOException;

    List<Flight> getAllFlightsByFilter(Date dateFrom, Date dateTo, String origin, String destination) throws IOException;

    List<Hotel> getAllHotelsByDestination(String destination) throws IOException;

    List<Flight> getAllFlightsByOrigin(String origin) throws IOException;

    List<Flight> getAllFlightsByDestination(String destination) throws IOException;
}
