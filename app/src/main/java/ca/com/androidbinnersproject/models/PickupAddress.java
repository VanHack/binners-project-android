package ca.com.androidbinnersproject.models;

/**
 * Created by jonathan_campos on 15/07/2016.
 */
public class PickupAddress {
    private String street;
    private String city;
    private String state;
    private String zip;
    private PickupLocation location;

    public PickupAddress() {
        location = new PickupLocation();
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

    public PickupLocation getLocation() {
        return location;
    }

    public void setLocation(PickupLocation location) {
        this.location = location;
    }
}
