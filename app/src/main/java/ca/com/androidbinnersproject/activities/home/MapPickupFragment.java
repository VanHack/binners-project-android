
package ca.com.androidbinnersproject.activities.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
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
import ca.com.androidbinnersproject.adapters.BaloonAdapter;
import ca.com.androidbinnersproject.helpers.GeoHelper;

public class MapPickupFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener,  LocationListener , GoogleMap.OnInfoWindowClickListener {


  private SupportMapFragment mSupportMapFragment;
  private GoogleMap mMapView;
  private MarkerOptions markerOptions;
  private Marker mMarker;
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
    mMapView.setInfoWindowAdapter(new BaloonAdapter(getActivity()));
    mMapView.setOnInfoWindowClickListener(this);
    mMapView.setOnMapClickListener(this);

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

  @Override
  public void onMapClick(LatLng latLng) {
    Toast.makeText(getContext(), latLng.toString(), Toast.LENGTH_SHORT).show();

    mMarker.remove();
    mMarker = mMapView.addMarker(new MarkerOptions().position(latLng));
    mMarker.showInfoWindow();
  }

  @Override
  public void onLocationChanged(Location location) {
    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
    mMarker = mMapView.addMarker(new MarkerOptions().position(loc));
    if(mMapView != null){
      mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
    }
    mMarker.showInfoWindow();
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onProviderEnabled(String provider) {
    Toast.makeText(getContext(), "Enabled new provider " + provider,
        Toast.LENGTH_SHORT).show();

  }

  @Override
  public void onProviderDisabled(String provider) {
    Toast.makeText(getContext(), "Disabled provider " + provider,
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onInfoWindowClick(Marker marker) {
    //Todo: go to Create Pickup Activity
    Toast.makeText(getContext(), "onInfoWindowClick", Toast.LENGTH_SHORT).show();
  }

  private void showCurrentLocation() {
    // Getting LocationManager object from System Service LOCATION_SERVICE
    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

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
      mMarker = mMapView.addMarker(new MarkerOptions().position(mLatLng));
      mMarker.showInfoWindow();
    }
  }

  private void onSearch(final String search) {
    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

    try {
      List<Address> addresses = geocoder.getFromLocationName(search, 5);

      if (addresses == null || addresses.size() == 0) {
        Toast.makeText(getActivity(), "No Location found", Toast.LENGTH_SHORT).show();
      } else {
        Address address = addresses.get(0);
        mMapView.clear();

        mLatLng = new LatLng(address.getLatitude(), address.getLongitude());
        markerOptions = new MarkerOptions();
        markerOptions.position(mLatLng);
        markerOptions.title(GeoHelper.getInstance(getContext()).getAddress(mLatLng));

        mMarker = mMapView.addMarker(markerOptions);
        mMarker.showInfoWindow();
      }

    } catch (IOException e) {
      Log.e("MAPSERROR", "");
    }
  }

  public LatLng getLatLng() {
    return mLatLng;
  }
}
