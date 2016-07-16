package ca.com.androidbinnersproject.models;

/**
 * Created by jonathan_campos on 15/07/2016.
 */
public class Location {
    private String type;
    private double[] coordinates;

    public Location() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
