package com.gmail.guushamm.unicare;

/**
 * Created by Nekkyou on 2-6-2016.
 */
public class Address {
    private String street;
    private String number;
    private String city;

    public Address(String street, String number, String city) {
        this.street = street;
        this.number = number;
        this.city = city;
    }

    public Address(String jsonString) {
        this.street = jsonString.substring(0, jsonString.indexOf(";"));
        this.number = jsonString.substring(jsonString.indexOf(";") + 1, jsonString.lastIndexOf(";"));
        this.city = jsonString.substring(jsonString.lastIndexOf(";") + 1);
    }

    //region getters and setters
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    //endregion

    public String toJson() {
        String jsonString = "";
        jsonString += getStreet() + ";" + getNumber() + ";" + getCity();
        return jsonString;
    }
}
