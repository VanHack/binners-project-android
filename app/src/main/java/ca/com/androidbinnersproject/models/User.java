package ca.com.androidbinnersproject.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jonathan_campos on 28/01/2016.
 */
public class User implements Serializable{
    @SerializedName("_id")
    private String id;
    private String name;
    private String email;
    private String password;

    public User(){}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
