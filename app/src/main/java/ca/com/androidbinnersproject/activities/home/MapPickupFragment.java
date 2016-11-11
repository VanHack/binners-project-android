
package ca.com.androidbinnersproject.activities.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.com.androidbinnersproject.R;

public class MapPickupFragment extends Fragment implements OnMapReadyCallback {


  private SupportMapFragment mSupportMapFragment;
  private GoogleMap mMapView;
  private MarkerOptions markerOptions;
  private LatLng mLatLng;
  private String mLastQuery = "";
  private Resources res;

  @BindView(R.id.pickup_search_bar)
  FloatingSearchView mSearchView;

  public static MapPickupFragment newInstance() {
    return new MapPickupFragment();
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
    ButterKnife.bind(this, view);
    res = getContext().getResources();

    mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_home_screen_map);
    mSupportMapFragment = SupportMapFragment.newInstance();

    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    transaction.add(R.id.fragment_home_screen_map, mSupportMapFragment).commit();

    setupSearch();

    return view;
  }


  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onResume() {
    super.onResume();

    mSupportMapFragment.getMapAsync(this);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMapView = googleMap;
    mMapView.setPadding(16,150,16,16);

    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      mMapView.setMyLocationEnabled(true);
      showCurrentLocation();
    } else {
      // Show rationale and request permission.
    }
  }

  public void setupSearch() {
    mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
      @Override
      public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        mLastQuery = searchSuggestion.getBody();
      }

      @Override
      public void onSearchAction(String query) {
        mLastQuery = query;
        onSearch(query);
      }
    });
  }


  private void showCurrentLocation() {
    // Getting LocationManager object from System Service LOCATION_SERVICE
    LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

    // Creating a criteria object to retrieve provider
    Criteria criteria = new Criteria();

    // Getting the name of the best provider
    String provider = locationManager.getBestProvider(criteria, true);

    // Getting Current Location
    Location location = null;

    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
      location = locationManager.getLastKnownLocation(provider);

    if (location != null) {
      // Creating a LatLng object for the current location
      mLatLng = new LatLng(location.getLatitude(), location.getLongitude());

      mMapView.clear();
      mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));
      mMapView.setInfoWindowAdapter(new BaloonAdapter(getActivity(), getActivity().getLayoutInflater()));
      mMapView.addMarker(new MarkerOptions().position(mLatLng).draggable(true)).showInfoWindow();
    }
  }

  private void onSearch(final String search) {
    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

    try {
      List<Address> addresses = geocoder.getFromLocationName(search, 5);

      if (addresses == null || addresses.size() == 0) {
        Toast.makeText(getActivity(), "No Location found", Toast.LENGTH_SHORT).show();
      }

      // Clears all the existing markers on the map
      mMapView.clear();

      // Adding Markers on Google Map for each matching address
      for (int i = 0; i < addresses.size(); i++) {

        Address address = (Address) addresses.get(i);

        // Creating an instance of GeoPoint, to display in Google Map
        mLatLng = new LatLng(address.getLatitude(), address.getLongitude());

        String addressText = String.format("%s, %s",
            address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
            address.getCountryName());

        markerOptions = new MarkerOptions();
        markerOptions.position(mLatLng);
        markerOptions.title(addressText);

        mMapView.addMarker(markerOptions);

        // Locate the first location
        if (i == 0)
          mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 14));
      }

    } catch (IOException e) {
      Log.e("MAPSERROR", "");
    }
  }

  public LatLng getLatLng() {
    return mLatLng;
  }
}
