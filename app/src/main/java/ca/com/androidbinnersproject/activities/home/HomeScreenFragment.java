
package ca.com.androidbinnersproject.activities.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.pickup.PickupActivity;

public class HomeScreenFragment extends Fragment implements OnMapReadyCallback {
    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mMapView;

	private Toolbar mToolbarBottom;

	public HomeScreenFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home_screen, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		mToolbarBottom = (Toolbar) view.findViewById(R.id.home_screen_include_toolbar);

		setToolbatClickListener();

		super.onViewCreated(view, savedInstanceState);
	}

	private void setToolbatClickListener() {
		mToolbarBottom.findViewById(R.id.toolbar_bottom_btnHistory).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		mToolbarBottom.findViewById(R.id.toolbar_bottom_btnOngoing).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		mToolbarBottom.findViewById(R.id.toolbar_bottom_btnPickup).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PickupActivity.class);
				startActivity(intent);
			}
		});

		mToolbarBottom.findViewById(R.id.toolbar_bottom_btnNotifications).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		mToolbarBottom.findViewById(R.id.toolbar_bottom_btnDonate).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}

	@Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentManager fm = getChildFragmentManager();
        mSupportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);

        if (mSupportMapFragment == null) {
            mSupportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, mSupportMapFragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mSupportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapView = googleMap;

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMapView.setMyLocationEnabled(true);

            showCurrentLocation();
        } else {
            // Show rationale and request permission.
        }
    }

    private void showCurrentLocation() {
        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if(location!=null) {
            // Creating a LatLng object for the current location
            LatLng myPosition = new LatLng(location.getLatitude(), location.getLongitude());

            mMapView.clear();

            mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));

            mMapView.addMarker(new MarkerOptions().position(myPosition).title(getActivity().getResources().getString(R.string.home_screen_map_marker_title))).showInfoWindow();
        }
    }
}
