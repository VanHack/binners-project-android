package ca.com.androidbinnersproject.helpers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;

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

  public static String getAddressByLatLng(Context context, Marker marker) {
    List<Address> addresses = new ArrayList<>();
    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
    double latitude = marker.getPosition().latitude;
    double longitude = marker.getPosition().longitude;
    try {
      addresses = geocoder.getFromLocation(latitude, longitude, 1);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return addresses.get(0).getAddressLine(0);

  }
}
