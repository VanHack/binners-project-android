package ca.com.androidbinnersproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.pickup.NewPickupFragment;
import ca.com.androidbinnersproject.helpers.GeoHelper;

/**
 * Created by dev on 11/5/2016.
 */

public class BaloonAdapter implements GoogleMap.InfoWindowAdapter {
  LayoutInflater mInflater;
  Context mContext;
  @BindView(R.id.pickup_baloon_address)
  TextView address;
  @BindView(R.id.pickup_baloon_city)
  TextView city;
  @BindView(R.id.pickup_baloon_request)
  TextView request;


  public BaloonAdapter(Context context) {
    this.mInflater = ((AppCompatActivity) context).getLayoutInflater();
    this.mContext = context;
  }

  @Override
  public View getInfoWindow(Marker marker) {
    View view = mInflater.inflate(R.layout.pickup_baloon, null);
    ButterKnife.bind(this, view);

    address.setText(GeoHelper.getInstance(mContext).getAddress(marker.getPosition()));
    city.setText(GeoHelper.getInstance(mContext).getCity(marker.getPosition()));
    return view;
  }

  @Override
  public View getInfoContents(Marker marker) {
    return null;
  }
}
