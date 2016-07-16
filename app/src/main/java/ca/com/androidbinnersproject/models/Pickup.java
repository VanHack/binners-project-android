package ca.com.androidbinnersproject.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.com.androidbinnersproject.util.Util;

/**
 * Created by jonathan_campos on 27/06/2016.
 */
public class Pickup {
    @SerializedName(value = "requester")
    private String userID;
    private Address address;
    private transient Calendar dateTime;
    private long time;
    private String instructions;
    private List<Items> items;

    public Pickup() {
        address = new Address();
        items   = new ArrayList<>();
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
        this.time     = dateTime.getTimeInMillis();
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
