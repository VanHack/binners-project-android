package ca.com.androidbinnersproject.models;

import android.app.Application;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jonathan_campos on 18/01/2016.
 */
public class Profile extends Application implements Serializable {

  private String urlImage;
  private String token;
  private User user;

  private static Profile instance = null;

  public static Profile getInstance() {
    if (instance == null) {
      instance = new Profile();
    }
    return instance;
  }

  public String getName() {
    return user.getName();
  }

  public void setName(String name) {
    user.setName(name);
  }

  public String getEmail() {
    return user.getEmail();
  }

  public void setEmail(String email) {
    user.setEmail(email);
  }

  public String getId() {
    return user.getId();
  }

  public void setId(String id) {
    user.setId(id);
  }

  public String getUrlImage() {
    return urlImage;
  }

  public void setUrlImage(String urlImage) {
    this.urlImage = urlImage;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean hasUser() {
    return user != null;
  }
}
