package ca.com.androidbinnersproject.models;

/**
 * Created by jonathan_campos on 15/07/2016.
 */
public class Location {
    private String type;
    private double[] coordinates;
    private double latitude;
    private double longitude;

    public Location() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.latitude;
    }

    public void setCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
