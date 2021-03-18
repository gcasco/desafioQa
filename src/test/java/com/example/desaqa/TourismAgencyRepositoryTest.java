package com.example.desaqa;

import com.example.desaqa.repository.ITourismAgencyRepository;
import com.example.desaqa.repository.TourismAgencyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TourismAgencyRepositoryTest {

    private ITourismAgencyRepository repository;

    @BeforeEach
    void init(){
        repository=new TourismAgencyRepository();
    }

    @Test
    public void testGetAllHotels() throws IOException {
        Assertions.assertDoesNotThrow(()->repository.getAllHotels());
        Assertions.assertTrue(repository.getAllHotels().size()>0);
        Assertions.assertTrue(repository.getAllHotels().get(1).getName().contains("Cataratas Hotel"));
    }

    @Test
    public void testGetAllHotelsByFilter() throws IOException, ParseException {
        Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2021");
        Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse("14/03/2021");
        String location = "Puerto Iguazú";

        Assertions.assertDoesNotThrow(()->repository.getAllHotelsByFilter(dateTo,dateTo,location));
        Assertions.assertDoesNotThrow(()->repository.getAllHotelsByFilter(dateFrom,dateFrom,location));
        Assertions.assertDoesNotThrow(()->repository.getAllHotelsByFilter(dateFrom,dateTo,location));
        Assertions.assertTrue(repository.getAllHotelsByFilter(dateFrom,dateTo,location).size()>0);
        Assertions.assertThrows(NullPointerException.class,()-> repository.getAllHotelsByFilter(null,null,null));
    }

    @Test
    public void testGetAllFlightsByFilter() throws IOException, ParseException {
        Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse("11/02/2021");
        Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse("13/02/2021");
        String destination = "Puerto Iguazú";
        String origin = "Buenos Aires";

        Assertions.assertDoesNotThrow(()->repository.getAllFlightsByFilter(dateTo,dateTo,origin,destination));
        Assertions.assertDoesNotThrow(()->repository.getAllFlightsByFilter(dateFrom,dateFrom,origin,destination));
        Assertions.assertDoesNotThrow(()->repository.getAllFlightsByFilter(dateFrom,dateTo,origin,destination));
        Assertions.assertTrue(repository.getAllFlightsByFilter(dateFrom,dateTo,origin,destination).size()>0);
        Assertions.assertThrows(NullPointerException.class,()-> repository.getAllHotelsByFilter(null,null,null));
    }

    @Test
    public void testGetAllHotelsByDestination() throws IOException {
        String location = "Puerto Iguazú";
        String locationinvalid = "Puerto";

        Assertions.assertTrue(repository.getAllHotelsByDestination(location).size()>0);
        Assertions.assertTrue(repository.getAllHotelsByDestination(locationinvalid).isEmpty());
    }

    @Test
    public void testGetAllFlightsByDestination() throws IOException {
        String location = "Puerto Iguazú";
        String locationinvalid = "Puerto";

        Assertions.assertTrue(repository.getAllFlightsByDestination(locationinvalid).isEmpty());
        Assertions.assertTrue(repository.getAllFlightsByDestination(location).size()>0);
        Assertions.assertDoesNotThrow(()->repository.getAllFlightsByDestination(location));
    }

    @Test
    public void testGetAllFlightsByOrigin() throws IOException {
        String origin = "Buenos Aires";
        String originInvalid = "Hawai";

        Assertions.assertTrue(repository.getAllFlightsByOrigin(originInvalid).isEmpty());
        Assertions.assertTrue(repository.getAllFlightsByOrigin(origin).size()>0);
        Assertions.assertDoesNotThrow(()->repository.getAllFlightsByOrigin(origin));
    }
}
