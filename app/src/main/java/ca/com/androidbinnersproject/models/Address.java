package ca.com.androidbinnersproject.models;

/**
 * Created by jonathan_campos on 15/07/2016.
 */
public class Address {
    private String street;
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


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return street;
    }
}
