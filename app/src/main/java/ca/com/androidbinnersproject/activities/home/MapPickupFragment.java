
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
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.MainActivity;
import ca.com.androidbinnersproject.activities.pickup.PickupActivity;
import ca.com.androidbinnersproject.adapters.BaloonAdapter;
import ca.com.androidbinnersproject.helpers.GeoHelper;

public class MapPickupFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener,  LocationListener , GoogleMap.OnInfoWindowClickListener {


  private GoogleMap mGoogleMap;
  private MarkerOptions markerOptions;
  private Marker mMarker;
  private LatLng mLatLng;
  private String mLastQuery = "";
  private Resources res;
  private MainActivity activity;

  @BindView(R.id.pickup_search_bar)
  FloatingSearchView mSearchView;
  @BindView(R.id.pickup_map_view)
  MapView mMapView;

  public static MapPickupFragment newInstance() {
    return new MapPickupFragment();
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
    ButterKnife.bind(this, view);
    res = getContext().getResources();
    activity = (MainActivity) getActivity();

    mMapView.onCreate(savedInstanceState);
    mMapView.onResume();
    mMapView.getMapAsync(this);
    setupSearch();

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    mMapView.onResume();
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

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mGoogleMap = googleMap;
    mGoogleMap.setPadding(16,150,16,16);
    mGoogleMap.setInfoWindowAdapter(new BaloonAdapter(activity));
    mGoogleMap.setOnInfoWindowClickListener(this);
    mGoogleMap.setOnMapClickListener(this);

    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      mGoogleMap.setMyLocationEnabled(true);
      showCurrentLocation();
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
    mMarker.remove();
    mMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng));
    mMarker.showInfoWindow();
  }

  @Override
  public void onLocationChanged(Location location) {
    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
    mMarker = mGoogleMap.addMarker(new MarkerOptions().position(loc));
    if(mGoogleMap != null){
      mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
    }
    mMarker.showInfoWindow();
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

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
    PickupActivity.startNewPickup(getActivity(), marker.getPosition().latitude, marker.getPosition().longitude);
  }

  private void showCurrentLocation() {
    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

    Criteria criteria = new Criteria();

    String provider = locationManager.getBestProvider(criteria, true);

    Location location = null;

    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
      location = locationManager.getLastKnownLocation(provider);

    if (location != null) {
      mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
      mGoogleMap.clear();
      mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));
      mMarker = mGoogleMap.addMarker(new MarkerOptions().position(mLatLng));
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
        mGoogleMap.clear();

        mLatLng = new LatLng(address.getLatitude(), address.getLongitude());
        markerOptions = new MarkerOptions();
        markerOptions.position(mLatLng);
        markerOptions.title(GeoHelper.getInstance(getContext()).getAddress(mLatLng));

        mMarker = mGoogleMap.addMarker(markerOptions);
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
