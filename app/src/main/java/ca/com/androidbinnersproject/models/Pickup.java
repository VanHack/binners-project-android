package ca.com.androidbinnersproject.models;

import java.util.Calendar;

/**
 * Created by jonathan_campos on 27/06/2016.
 */
public class Pickup {
    private String userID;
    private PickupAddress pickupAddress;
    private Calendar dateTime;
    private String instructions;

    public Pickup() {
        pickupAddress = new PickupAddress();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public PickupAddress getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(PickupAddress pickupAddress) {
        this.pickupAddress = pickupAddress;
    }
}
