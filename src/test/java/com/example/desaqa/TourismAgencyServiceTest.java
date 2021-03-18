package com.example.desaqa;

import com.example.desaqa.controller.TourismAgencyController;
import com.example.desaqa.exception.GenericException;
import com.example.desaqa.exception.ParamDateToInvalidException;
import com.example.desaqa.exception.ParamDestinationInvalidException;
import com.example.desaqa.exception.ParamOriginInvalidException;
import com.example.desaqa.repository.ITourismAgencyRepository;
import com.example.desaqa.service.ITourismAgencyService;
import com.example.desaqa.service.TourismAgencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.MockitoAnnotations.openMocks;

public class TourismAgencyServiceTest {

    ITourismAgencyService service;
    @Mock
    private ITourismAgencyRepository repository;

    @BeforeEach
    public  void setService(){
        openMocks(this);
        this.service = new TourismAgencyService(this.repository);
    }
    @BeforeEach
    void init(){
        service=new TourismAgencyService();
    }

    @Test
    public void testGetListHotels() throws IOException {
        Assertions.assertTrue(service.getListHotels().size()>0);
        Assertions.assertTrue(service.getListHotels().get(1).getName().contains("Cataratas Hotel"));
    }

    @Test
    public void testGetListHotelsByFilter() throws ParseException, IOException, GenericException {
        Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2021");
        Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse("14/03/2021");
        String location = "Puerto Iguazú";

        Assertions.assertThrows(ParamDateToInvalidException.class,()->service.getListHotelsByFilters(dateTo,dateTo,location));
        Assertions.assertThrows(ParamDateToInvalidException.class,()->service.getListHotelsByFilters(dateFrom,dateFrom,location));
        Assertions.assertDoesNotThrow(()->service.getListHotelsByFilters(dateFrom,dateTo,location));
        Assertions.assertTrue(service.getListHotelsByFilters(dateFrom,dateTo,location).size()>0);
        Assertions.assertThrows(NullPointerException.class,()-> service.getListHotelsByFilters(null,null,null));
        Assertions.assertThrows(ParamDateToInvalidException.class,()-> service.getListHotelsByFilters(dateTo,dateFrom,"null"));
        Assertions.assertThrows(ParamDestinationInvalidException.class,()-> service.getListHotelsByFilters(dateFrom,dateTo,""));
    }
    @Test
    public void testGetListFlightsByFilter() throws ParseException, IOException, GenericException {
        Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse("11/02/2021");
        Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse("13/02/2021");
        String destination = "Puerto Iguazú";
        String origin = "Buenos Aires";

        Assertions.assertTrue(service.getListFlightsByFilters(dateFrom,dateTo,origin,destination).size()>0);
        Assertions.assertDoesNotThrow(()->service.getListFlightsByFilters(dateFrom,dateTo,origin,destination));
        Assertions.assertThrows(ParamOriginInvalidException.class,()->service.getListFlightsByFilters(dateFrom,dateTo,"hawai",destination));
        Assertions.assertThrows(ParamDestinationInvalidException.class,()->service.getListFlightsByFilters(dateFrom,dateTo,origin,"hawai"));
        Assertions.assertThrows(ParamDateToInvalidException.class,()->service.getListFlightsByFilters(dateFrom,dateFrom,origin,destination));
    }
}
