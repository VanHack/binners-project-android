package ca.com.androidbinnersproject.models;

/**
 * Created by jonathan_campos on 15/07/2016.
 */
public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;
    private Location location;

    public Address() {
        location = new Location();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}
