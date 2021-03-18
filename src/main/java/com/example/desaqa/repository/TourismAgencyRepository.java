package com.example.desaqa.repository;

import com.example.desaqa.exception.ParamInputInvalidException;
import com.example.desaqa.model.Flight;
import com.example.desaqa.model.Hotel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TourismAgencyRepository implements ITourismAgencyRepository{

    private static List<Hotel> hotelsRepository = null;
    private static List<Flight> flightsRepository = null;

    public void loadDataBase() throws IOException {
        loadHotels();
        loadFlights();
    }

    public void loadHotels() throws IOException {
        File file = null;
        file = ResourceUtils.getFile("classpath:hotels.json");

        ObjectMapper objectMapper = new ObjectMapper();
        com.fasterxml.jackson.core.type.TypeReference<List<Hotel>> typeReference =
                new com.fasterxml.jackson.core.type.TypeReference<>(){};
        hotelsRepository = objectMapper.readValue(file,typeReference);
    }

    public void loadFlights() throws IOException {
        File file = null;
        file = ResourceUtils.getFile("classpath:flights.json");

        ObjectMapper objectMapper = new ObjectMapper();
        com.fasterxml.jackson.core.type.TypeReference<List<Flight>> typeReference =
                new com.fasterxml.jackson.core.type.TypeReference<>(){};
        flightsRepository = objectMapper.readValue(file,typeReference);
    }

    @Override
    public List<Hotel> getAllHotels() throws IOException {
        if(hotelsRepository==null){
            loadDataBase();
        }
        return hotelsRepository;
    }

    @Override
    public List<Flight> getAllFlights() throws IOException {
        if(flightsRepository==null){
            loadDataBase();
        }
        return flightsRepository;
    }

    @Override
    public List<Hotel> getAllHotelsByFilter(Date dateFrom, Date dateTo, String destination) throws IOException {
        List<Hotel> hotelsByFilters= getAllHotels();
        var tmpHotels = hotelsByFilters.stream()
                .filter(hotel -> hotel.getDestination().toLowerCase().contains(destination.toLowerCase())
                                    && hotel.getAvailableFrom().getTime().compareTo(dateFrom)<=0
                                  && hotel.getAvailableTo().getTime().compareTo(dateTo)>=0

                )
                .collect(Collectors.toList());
        return tmpHotels;
    }

    @Override
    public List<Flight> getAllFlightsByFilter(Date dateFrom, Date dateTo, String origin, String destination) throws IOException {
        List<Flight> flightsByFilters= getAllFlights();
        var tmpFlights = flightsByFilters.stream()
                .filter(flight -> flight.getDestination().toLowerCase().contains(destination.toLowerCase())
                        && flight.getOrigin().toLowerCase().contains(origin.toLowerCase())
                        && flight.getDepartureDate().getTime().compareTo(dateFrom)<=0
                        && flight.getReturnDate().getTime().compareTo(dateTo)>=0

                )
                .collect(Collectors.toList());
        return tmpFlights;
    }

    @Override
    public List<Hotel> getAllHotelsByDestination(String destination) throws IOException {
        List<Hotel> hotelsByFilters= getAllHotels();
        var tmpHotels = hotelsByFilters.stream()
                .filter(hotel -> hotel.getDestination().toLowerCase().equals(destination.toLowerCase()))
                .collect(Collectors.toList());
        return tmpHotels;
    }
    @Override
    public List<Flight> getAllFlightsByOrigin(String origin) throws IOException {
        List<Flight> flightsByFilters= getAllFlights();
        var tmpFlights = flightsByFilters.stream()
                .filter(flight -> flight.getOrigin().toLowerCase().equals(origin.toLowerCase()))
                .collect(Collectors.toList());
        return tmpFlights;
    }

    @Override
    public List<Flight> getAllFlightsByDestination(String destination) throws IOException {
        List<Flight> flightsByFilters= getAllFlights();
        var tmpFlights = flightsByFilters.stream()
                .filter(flight -> flight.getDestination().toLowerCase().equals(destination.toLowerCase()))
                .collect(Collectors.toList());
        return tmpFlights;
    }

    @Override
    public Hotel getHotel(String hotelCode) throws ParamInputInvalidException, IOException {
        for (Hotel p: getAllHotels()   ) {
            if(p.getCode().equals(hotelCode)){
                return p;
            }
        }
        throw new ParamInputInvalidException("El codigo de Hotel ingresado es inválido");
    }

    @Override
    public Flight getFlight(String flightNumbre) throws IOException, ParamInputInvalidException {
        for (Flight p: getAllFlights()   ) {
            if(p.getCode().equals(flightNumbre)){
                return p;
            }
        }
        throw new ParamInputInvalidException("El codigo de Vuelo ingresado es inválido");
    }
}
