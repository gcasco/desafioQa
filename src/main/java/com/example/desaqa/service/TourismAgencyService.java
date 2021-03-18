package com.example.desaqa.service;

import com.example.desaqa.exception.*;
import com.example.desaqa.model.*;
import com.example.desaqa.repository.ITourismAgencyRepository;
import com.example.desaqa.repository.TourismAgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class TourismAgencyService implements ITourismAgencyService{

    @Autowired
    private ITourismAgencyRepository repository;

    public TourismAgencyService() {
        repository=new TourismAgencyRepository();
    }

    public TourismAgencyService(ITourismAgencyRepository repository) {
        this.repository=repository;
    }

    @Override
    public Hotel getHotel(String hotelCode) throws IOException, ParamInputInvalidException {
        return repository.getHotel(hotelCode);
    }

    @Override
    public Flight getFlight(String flightNumbre) throws IOException, ParamInputInvalidException {
        return repository.getFlight(flightNumbre);
    }

    @Override
    public List<Hotel> getListHotels() throws IOException {
        return repository.getAllHotels();
    }

    @Override
    public List<Flight> getListFlights() throws IOException {
        return repository.getAllFlights();
    }

    @Override
    public List<Hotel> getListHotelsByFilters(Date dateFrom, Date dateTo, String destination) throws IOException, GenericException {
        List<Hotel> result= new ArrayList<>();
        if(isValidDateRange(dateFrom,dateTo) && existHotelDestination(destination)){
            result = repository.getAllHotelsByFilter(dateFrom,dateTo,destination);
        }
        return result;
    }

    @Override
    public List<Flight> getListFlightsByFilters(Date dateFrom, Date dateTo, String origin, String destination) throws IOException, GenericException {
        List<Flight> result= new ArrayList<>();
        if(isValidDateRange(dateFrom,dateTo) && existFlightOrigin(origin) && existFlightDestination(destination)){
            result = repository.getAllFlightsByFilter(dateFrom,dateTo,origin,destination);
        }
        return result;
    }

    @Override
    public Boolean isValidDateRange(Date dateFrom, Date dateTo) throws GenericException {
        Boolean valid=true;
        if(dateFrom.compareTo(dateTo)>=0){
            throw new ParamDateToInvalidException();
        }
        return valid;
    }


    @Override
    public Boolean existHotelDestination(String destination) throws IOException, GenericException {
        Boolean valid=true;
        var listResult=repository.getAllHotelsByDestination(destination);
        if(listResult.isEmpty() || listResult.equals(null)){
            throw new ParamDestinationInvalidException();
        }
        return valid;
    }

    @Override
    public Boolean existFlightDestination(String destination) throws IOException, GenericException {
        Boolean valid=true;
        var listResult=repository.getAllFlightsByDestination(destination);
        if(listResult.isEmpty() || listResult.equals(null)){
            throw new ParamDestinationInvalidException();
        }
        return valid;
    }


    @Override
    public Boolean existFlightOrigin(String origin) throws IOException, GenericException {
        Boolean valid=true;
        var listResult=repository.getAllFlightsByOrigin(origin);
        if(listResult.isEmpty() || listResult.equals(null)){
            throw new ParamOriginInvalidException();
        }
        return valid;
    }

    @Override
    public Double calculateInterestForCredictCard(Integer dues) {
        var divResult =  (dues+0.0)/3;
        return Math.ceil(divResult)*5 ;
    }

    @Override
    public Integer getReserveDays(Date dateFrom, Date dateTo) {
        long diferenciaEn_ms = dateTo.getTime()-dateFrom.getTime();
        long dias = diferenciaEn_ms/(1000*60*60*24);
        return (int) dias;
    }

    @Override
    public Double calculateAmountOfReservation(Double price, Integer daysORseat) {
        return price*daysORseat;
    }

    @Override
    public HotelReservationDTO createReservationHotel(HotelReservationDTO reservationDTO) throws IOException, GenericException {
        if(isValidDateRange(reservationDTO.getBooking().getDateFrom(), reservationDTO.getBooking().getDateTo())
                && existHotelDestination(reservationDTO.getBooking().getDestination()))
        {
            Hotel hotel = getHotel(reservationDTO.getBooking().getHotelCode());
            Double amount= calculateAmountOfReservation(hotel.getPricePerNight(),getReserveDays(reservationDTO.getBooking().getDateFrom(), reservationDTO.getBooking().getDateTo()));
            Double interest=0.0;
            Double total = amount;
            if(reservationDTO.getBooking().getPaymentMethod().getType().toUpperCase(Locale.ROOT).equals("CREDIT")){
                interest = calculateInterestForCredictCard(reservationDTO.getBooking().getPaymentMethod().getDues());
                total = amount+(amount*interest/100);
            }
            reservationDTO.setAmount(amount);
            reservationDTO.setInterest(interest);
            reservationDTO.setTotal(total);

            return reservationDTO;
        }
        return null;
    }

    @Override
    public FlightReservationDTO createReservationFlight(FlightReservationDTO reservationDTO) throws GenericException, IOException {
        if(isValidDateRange(reservationDTO.getFlightReservation().getDateFrom(), reservationDTO.getFlightReservation().getDateTo())
                && existFlightOrigin(reservationDTO.getFlightReservation().getOrigin())
                && existFlightDestination(reservationDTO.getFlightReservation().getDestination()))
        {
            Flight flight = getFlight(reservationDTO.getFlightReservation().getFlightNumber());
            Double amount= calculateAmountOfReservation(flight.getPricePerPerson(),reservationDTO.getFlightReservation().getSeats());
            Double interest=0.0;
            Double total = amount;
            if(reservationDTO.getFlightReservation().getPaymentMethod().getType().toUpperCase(Locale.ROOT).equals("CREDIT")){
                interest = calculateInterestForCredictCard(reservationDTO.getFlightReservation().getPaymentMethod().getDues());
                total = amount+(amount*interest/100);
            }
            reservationDTO.setAmount(amount);
            reservationDTO.setInterest(interest);
            reservationDTO.setTotal(total);

            return reservationDTO;
        }
        return null;
    }
}

