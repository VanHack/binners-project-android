package ca.com.androidbinnersproject.models;

/**
 * Created by jonathan_campos on 15/07/2016.
 */
public class Address {
    private String street;
    private String city;
    private Location location;

    public Address(Location location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Location getLocation() {
        return location;
    }


    @Override
    public String toString() {
        return street;
    }
}
