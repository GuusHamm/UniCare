package com.gmail.guushamm.unicare;

/**
 * Created by Nekkyou on 2-6-2016.
 */
public class CustomAddress {
    private String street;
    private String number;
    private String city;
    private String postcode;

    public CustomAddress(String street, String number, String city, String postcode) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.postcode = postcode;
    }



    public CustomAddress(String jsonString) {
        int firstSeperator = jsonString.indexOf(";");
        int secondSeperator = jsonString.indexOf(";", firstSeperator + 1);
        int lastSeperator = jsonString.lastIndexOf(";");

        System.out.println(jsonString);

        this.street = jsonString.substring(0, firstSeperator);
        this.number = jsonString.substring(firstSeperator + 1, secondSeperator);
        this.city = jsonString.substring(secondSeperator + 1, lastSeperator);
        this.postcode = jsonString.substring(lastSeperator + 1);
    }

    //region getters and setters

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

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
        jsonString += getStreet() + ";" + getNumber() + ";" + getCity() + ";" + getPostcode();
        return jsonString;
    }

    public String toString() {
        return String.format("%s %s, %s %s", getStreet(), getNumber(), getPostcode(), getCity());
    }
}
