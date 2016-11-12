package ca.com.androidbinnersproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

/**
 * Created by Jonathan on 28/10/2016.
 */
public class HistoryViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
    public TextView timeTv;
    public TextView dateTv;
    public TextView binnerTv;
    public TextView statusTv;

    protected GoogleMap googleMap;
    protected Pickup pickup;

    public MapView mapView;
    private Context ctx;

    public HistoryViewHolder(Context ctx, View itemView) {
        super(itemView);

        this.ctx = ctx;
        this.timeTv = (TextView) itemView.findViewById(R.id.txtHistoryTime);
        this.dateTv = (TextView) itemView.findViewById(R.id.txtHistoryDate);
        this.binnerTv = (TextView) itemView.findViewById(R.id.txtHistoryBinner);
        this.statusTv = (TextView) itemView.findViewById(R.id.txtStatusHistory);
        this.mapView = (MapView) itemView.findViewById(R.id.cardview_history_pickup_detail_map);

        this.mapView.onCreate(null);
        this.mapView.getMapAsync(this);
    }

    public void setPickup(Pickup pickup) {
        this.pickup = pickup;

        if (googleMap != null) {
            updateMapContents();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        MapsInitializer.initialize(this.ctx);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        if (this.pickup != null) {
            updateMapContents();
        }
    }

    protected void updateMapContents() {
        this.googleMap.clear();

        double latitude = 0;
        double longitude= 0;

        try {
            latitude = pickup.getAddress().getLocation().getLatitude();
            longitude = pickup.getAddress().getLocation().getLongitude();
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.Error("Latitude or Longitude with no value!");
        }

        if (latitude != 0 && longitude != 0) {
            LatLng latLng;
            latLng = new LatLng(latitude, longitude);

            this.googleMap.addMarker(new MarkerOptions().position(latLng));

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16f);
            this.googleMap.moveCamera(cameraUpdate);
        }
    }

}
