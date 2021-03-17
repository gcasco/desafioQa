package com.example.desaqa.service;

import com.example.desaqa.exception.GenericException;
import com.example.desaqa.exception.ParamDateToInvalidException;
import com.example.desaqa.exception.ParamDestinationInvalidException;
import com.example.desaqa.exception.ParamOriginInvalidException;
import com.example.desaqa.model.Flight;
import com.example.desaqa.model.Hotel;
import com.example.desaqa.repository.ITourismAgencyRepository;
import com.example.desaqa.repository.TourismAgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TourismAgencyService implements ITourismAgencyService{

    @Autowired
    private ITourismAgencyRepository repository;

    public TourismAgencyService() {
        repository=new TourismAgencyRepository();
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
}

