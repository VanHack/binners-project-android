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

/**
 * Created by Pedro Henrique on 06/07/2016.
 */
public class OngoingViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
    public TextView timeTv;
    public TextView dateTv;
    public TextView binnerTv;
    public TextView statusTv;

    protected GoogleMap googleMap;
    protected Pickup pickup;

    public MapView mapView;
    private Context ctx;

    public OngoingViewHolder(Context ctx, View itemView) {
        super(itemView);

        this.ctx = ctx;
        this.timeTv = (TextView) itemView.findViewById(R.id.timeTv);
        this.dateTv = (TextView) itemView.findViewById(R.id.dateTv);
        this.binnerTv = (TextView) itemView.findViewById(R.id.binnerTv);
        this.statusTv = (TextView) itemView.findViewById(R.id.statusTv);
        this.mapView = (MapView) itemView.findViewById(R.id.map);

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

        double latitude = pickup.getAddress().getLocation().getCoordinates()[0];
        double longitude= pickup.getAddress().getLocation().getCoordinates()[1];

        if (latitude != 0 && longitude != 0) {
            LatLng latLng;
            latLng = new LatLng(latitude, longitude);

            this.googleMap.addMarker(new MarkerOptions().position(latLng));

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16f);
            this.googleMap.moveCamera(cameraUpdate);
        }
    }

}
