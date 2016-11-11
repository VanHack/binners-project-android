package ca.com.androidbinnersproject.helpers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

/**
 * Created by dev on 11/5/2016.
 */

public class GeoHelper {

  private static GeoHelper instance;
  private Geocoder geocoder;
  List<Address> addresses = new ArrayList<>();

  public static GeoHelper getInstance(Context context) {
    if (instance == null) {
      instance = new GeoHelper(context);
    }
    return  instance;
  }

  private GeoHelper(Context context) {
    geocoder = new Geocoder(context, Locale.getDefault());
  }

  public String getAddress(LatLng position) {
    try {
      addresses = geocoder.getFromLocation(position.latitude, position.longitude, 10);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return addresses.get(0).getAddressLine(0);
  }

  public String getCity(LatLng position) {
    try {
      addresses = geocoder.getFromLocation(position.latitude, position.longitude, 1);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return addresses.get(0).getLocality();
  }

}
