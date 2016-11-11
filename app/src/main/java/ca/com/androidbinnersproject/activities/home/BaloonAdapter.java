package ca.com.androidbinnersproject.activities.home;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import butterknife.ButterKnife;
import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.helpers.GeoHelper;

/**
 * Created by dev on 11/5/2016.
 */

public class BaloonAdapter implements GoogleMap.InfoWindowAdapter {
  LayoutInflater mInflater;
  TextView mTextView;
  Context mContext;

  public BaloonAdapter(Context context, LayoutInflater inflater) {
    this.mInflater = inflater;
    this.mContext = context;
  }

  @Override
  public View getInfoWindow(Marker marker) {
    View view = mInflater.inflate(R.layout.pickup_baloon, null);


    mTextView = ButterKnife.findById(view, R.id.pickup_baloon_text);
    String address = GeoHelper.getAddressByLatLng(mContext, marker);
    mTextView.setText(address);
    return view;
  }

  @Override
  public View getInfoContents(Marker marker) {
    return null;
  }
}
