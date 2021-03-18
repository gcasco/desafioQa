package com.example.desaqa.controller;

import com.example.desaqa.exception.GenericException;
import com.example.desaqa.exception.ParamInputInvalidException;
import com.example.desaqa.model.*;
import com.example.desaqa.service.ITourismAgencyService;
import com.example.desaqa.service.ITourismAgencyValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
public class TourismAgencyController {
    @Autowired
    private ITourismAgencyService service;
    @Autowired
    private ITourismAgencyValidatorService validator;

    public TourismAgencyController(ITourismAgencyService service) {
        this.service=service;
    }

    @GetMapping(path = "/api/v1/hotels")
    @ResponseBody
    public List<Hotel> getAllAvailableHotels(@RequestParam(name="dateFrom",required = false) @DateTimeFormat(pattern="dd/MM/yyyy") Date dateFrom,
                                             @RequestParam(name="dateTo",required = false) @DateTimeFormat(pattern="dd/MM/yyyy") Date dateTo,
                                             @RequestParam(name="destination",required = false) String destination) throws ParseException, GenericException, IOException {

        if(dateFrom==null && dateTo ==null && destination==null) {
            return  service.getListHotels();
        }
        else if(dateFrom!=null && dateTo !=null && destination!=null){
            return service.getListHotelsByFilters(dateFrom,dateTo,destination);
        }else{
            throw new ParamInputInvalidException("Debe ingresar fecha desde, fecha hasta y destino (o ninguno)");
        }

    }


    @GetMapping(path = "/api/v1/flights")
    @ResponseBody
    public List<Flight> getAllAvailableFlights(@RequestParam(name="dateFrom",required = false) @DateTimeFormat(pattern="dd/MM/yyyy") Date dateFrom,
                                               @RequestParam(name="dateTo",required = false) @DateTimeFormat(pattern="dd/MM/yyyy") Date dateTo,
                                               @RequestParam(name="origin",required = false) String origin,
                                               @RequestParam(name="destination",required = false) String destination) throws ParseException, GenericException, IOException {

        if(dateFrom==null && dateTo ==null && origin ==null && destination==null) {
            return  service.getListFlights();
        }
        else if(dateFrom!=null && dateTo !=null && origin != null && destination!=null){
            return service.getListFlightsByFilters(dateFrom,dateTo,origin,destination);
        }else{
            throw new ParamInputInvalidException("Debe ingresar fecha desde, fecha hasta, origen y destino (o ninguno)");
        }

    }

    @PostMapping(path = "/api/v1/booking")
    @ResponseBody
    public HotelReservationDTO createHotelReservation(@RequestBody HotelReservationDTO reservationDTO) throws GenericException, IOException {
        HotelReservationDTO response = null;
        if(validator.validateHotelReservationRequest(reservationDTO)){
            response=service.createReservationHotel(reservationDTO);
        }
        if(response!=null){
            response.setStatusCode(new StatusCodeDTO(HttpStatus.OK.value(),"El proceso termino satisfactoriamente"));
            return response;
        }else{
            throw new GenericException("Ocurrio un error al procesar la reserva",HttpStatus.BAD_REQUEST.value());
        }
    }

    @PostMapping(path = "/api/v1/flight-reservation")
    @ResponseBody
    public FlightReservationDTO createFlightReservation(@RequestBody FlightReservationDTO reservationDTO) throws GenericException, IOException {
        FlightReservationDTO response = null;
        if(validator.validateFlightReservationRequest(reservationDTO)){
            response=service.createReservationFlight(reservationDTO);
        }
        if(response!=null){
            response.setStatusCode(new StatusCodeDTO(HttpStatus.OK.value(),"El proceso termino satisfactoriamente"));
            return response;
        }else{
            throw new GenericException("Ocurrio un error al procesar la reserva",HttpStatus.BAD_REQUEST.value());
        }
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<StatusCodeDTO> handleException(GenericException ex){
        return  new ResponseEntity<>(new StatusCodeDTO(ex.getStatusCode(), ex.getMessage()),HttpStatus.BAD_REQUEST);
    }
}
