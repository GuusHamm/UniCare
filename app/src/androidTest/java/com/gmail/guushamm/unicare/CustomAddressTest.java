package com.gmail.guushamm.unicare;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by Nekkyou on 2-6-2016.
 */
public class CustomAddressTest {
    private String street;
    private String number;
    private String city;
    private String postcode;

    private CustomAddress customAddress;
    private CustomAddress jsonCustomAddress;

    @Before
    public void before() {
        street = "kerkstraat";
        number = "5";
        city = "Casteren";
        postcode = "5529 AK";

        customAddress = new CustomAddress(street, number, city, postcode);
        jsonCustomAddress = new CustomAddress(customAddress.toJson());
    }

    @Test
    public void checkAddress() {
        assertEquals(jsonCustomAddress.getStreet(), street);
        assertEquals(jsonCustomAddress.getNumber(), number);
        assertEquals(jsonCustomAddress.getCity(), city);
    }

}
