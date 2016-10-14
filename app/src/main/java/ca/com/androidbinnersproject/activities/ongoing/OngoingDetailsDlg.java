package ca.com.androidbinnersproject.activities.ongoing;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.Logger;
import ca.com.androidbinnersproject.util.Util;

/**
 * Created by jonathan_campos on 18/07/2016.
 */
public class OngoingDetailsDlg extends DialogFragment implements OnMapReadyCallback {
    private MapView mMapView;
    private ImageButton btnClose;
    private TextView txtStatus;
    private TextView txtAddress;
    private TextView txtTime;
    private TextView txtDate;
    private TextView txtBinner;
    private ImageView imgBottle;

    private ImageButton btnRate;

    private Pickup mPickup;
    private GoogleMap mGoogleMap;

    public static OngoingDetailsDlg newInstance(Pickup pickup) {
        OngoingDetailsDlg dlg = new OngoingDetailsDlg();
        dlg.mPickup = pickup;

        return dlg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cardview_ongoig_pickup_detail, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.cardview_ongoing_pickup_detail_map);
        mMapView.onCreate(savedInstanceState);

        setupWindow();

        return rootView;
    }

    private void setupWindow() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();

        if(window != null) {
            ColorDrawable transparent = new ColorDrawable(Color.TRANSPARENT);
            window.setBackgroundDrawable(transparent);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        btnClose   = (ImageButton) view.findViewById(R.id.cardview_ongoing_pickup_detail_btnClose);
        btnRate    = (ImageButton) view.findViewById(R.id.cardview_ongoing_pickup_detail_btnRate);
        txtStatus  = (TextView) view.findViewById(R.id.cardview_ongoing_pickup_detail_txtStatus);
        txtAddress = (TextView) view.findViewById(R.id.cardview_ongoing_pickup_detail_txtAddress);
        txtTime    = (TextView) view.findViewById(R.id.cardview_ongoing_pickup_detail_txtTime);
        txtDate    = (TextView) view.findViewById(R.id.cardview_ongoing_pickup_detail_txtDate);
        txtBinner  = (TextView) view.findViewById(R.id.cardview_ongoing_pickup_detail_txtBinner);
        imgBottle  = (ImageView)view.findViewById(R.id.cardview_ongoing_pickup_detail_imgBottles);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatePickupDlg dlg = RatePickupDlg.newInstance(mPickup);
                dlg.show(getFragmentManager(), "");
            }
        });

        mMapView.getMapAsync(this);

        updateUI();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    //TODO Retrieve Status, Binners' name and bottles' image from the server
    private void updateUI() {
        if(mPickup != null) {
            txtStatus.setText("In Progress");
            txtAddress.setText(mPickup.getAddress().toString());
            txtTime.setText(Util.getTimeFormated(mPickup.getTime()));
            txtDate.setText(Util.getDateFormated(mPickup.getTime()));
            txtBinner.setText("Adam");
            //imgBottle.setImageResource();
        }
    }

    @Override
    public void onResume() {
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);

        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        MapsInitializer.initialize(getContext());

        /**
         * Set GoogleMap toolbar, which allow user to open GoogleMaps and Routes, to false;
         */
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

        if(mPickup != null)
            updateMapContents();
    }

    protected void updateMapContents() {
        mGoogleMap.clear();

        double latitude = 0;
        double longitude= 0;

        try {
            latitude = mPickup.getAddress().getLocation().getCoordinates()[0];
            longitude = mPickup.getAddress().getLocation().getCoordinates()[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.Error("Latitude or Longitude with no value!");
        }

        if (latitude != 0 && longitude != 0) {
            LatLng latLng;
            latLng = new LatLng(latitude, longitude);

            mGoogleMap.addMarker(new MarkerOptions().position(latLng));

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16f);
            mGoogleMap.moveCamera(cameraUpdate);
        }
    }
}
