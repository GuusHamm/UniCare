package com.gmail.guushamm.unicare;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by Nekkyou on 2-6-2016.
 */
public class AddressTest {
    private String street;
    private String number;
    private String city;

    private Address address;
    private Address jsonAddress;

    @Before
    public void before() {
        street = "kerkstraat";
        number = "5";
        city = "Casteren";

        address = new Address(street, number, city);
        jsonAddress = new Address(address.toJson());
    }

    @Test
    public void checkAddress() {
        assertEquals(jsonAddress.getStreet(), street);
        assertEquals(jsonAddress.getNumber(), number);
        assertEquals(jsonAddress.getCity(), city);
    }

}
