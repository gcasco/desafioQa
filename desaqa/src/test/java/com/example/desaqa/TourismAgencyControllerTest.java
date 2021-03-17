package com.example.desaqa;

import com.example.desaqa.controller.TourismAgencyController;
import com.example.desaqa.exception.*;
import com.example.desaqa.model.Hotel;
import com.example.desaqa.service.ITourismAgencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.MockitoAnnotations.openMocks;

public class TourismAgencyControllerTest {
    private TourismAgencyController controller;

    @Mock
    private ITourismAgencyService service;

    @BeforeEach
    public  void setController(){
        openMocks(this);
        this.controller = new TourismAgencyController(this.service);
    }

    @Test
    public void testGetAllAvailableHotels() throws ParseException, GenericException, IOException {
        List<Hotel> resultService= new ArrayList<>();
        List<Hotel> resultController= new ArrayList<>();
        Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2021");
        Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse("14/03/2021");
        String destination = "Puerto Iguazú";

        Date dateFromInvalid = new SimpleDateFormat("MM/dd/yyyy").parse("14/02/2021");
        Date dateToInvalid = new SimpleDateFormat("MM/dd/yyyy").parse("02/14/2021");
        Date dateIncompletitude = new SimpleDateFormat("d/MM/yyyy").parse("2/14/2021");
        String destinationInvalid = "hawai";

        resultService=service.getListHotels();
        resultController = controller.getAllAvailableHotels(null,null,null);
        Assertions.assertIterableEquals(resultService,resultController);


        resultService=service.getListHotelsByFilters(dateFrom,dateTo,destination);
        resultController = controller.getAllAvailableHotels(dateFrom,dateTo,destination);
        Assertions.assertIterableEquals(resultService,resultController);

        Assertions.assertThrows(ParamInputInvalidException.class, ()->controller.getAllAvailableHotels(dateFrom,null,destination));
        Assertions.assertThrows(ParamInputInvalidException.class, ()->controller.getAllAvailableHotels(dateFrom,dateFrom,null));
        resultController=controller.getAllAvailableHotels(dateFrom,dateTo,destinationInvalid);
        resultService=service.getListHotelsByFilters(dateFrom,dateTo,destinationInvalid);
        Assertions.assertEquals(resultController,resultService);

    }

    @Test
    public void testGetAllAvalaibleFlights() throws ParseException, IOException, GenericException {
        Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse("11/02/2021");
        Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse("13/02/2021");
        String destination = "Puerto Iguazú";
        String origin="Buenos Aires";

        Assertions.assertIterableEquals(service.getListFlights(),controller.getAllAvailableFlights(null,null,null,null));
        Assertions.assertIterableEquals(service.getListFlightsByFilters(dateFrom,dateTo,origin,destination),controller.getAllAvailableFlights(dateFrom,dateTo,origin,destination));
        Assertions.assertThrows(ParamInputInvalidException.class,()->controller.getAllAvailableFlights(dateFrom,dateTo,origin,null));
        Assertions.assertDoesNotThrow(()->controller.getAllAvailableFlights(dateFrom,dateTo,origin,destination));

    }


}
