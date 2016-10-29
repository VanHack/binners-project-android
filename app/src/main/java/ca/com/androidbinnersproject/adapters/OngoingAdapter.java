package ca.com.androidbinnersproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.BinnersStatusConverter;

/**
 * Created by Pedro Henrique on 06/07/2016.
 */
public class OngoingAdapter extends RecyclerView.Adapter<OngoingViewHolder> {
    protected HashSet<MapView> mapViews = new HashSet<>();
    protected List<Pickup> pickups;

    public OngoingAdapter(List<Pickup> pickups) {
        this.pickups = pickups;
    }

    @Override
    public OngoingViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_ongoig_pickup, viewGroup, false);
        OngoingViewHolder viewHolder = new OngoingViewHolder(viewGroup.getContext(), view);

        mapViews.add(viewHolder.mapView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OngoingViewHolder viewHolder, int position) {
        Pickup pickup = pickups.get(position);

        viewHolder.itemView.setTag(pickup);

        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:MM");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        viewHolder.timeTv.setText( sdfTime.format(pickup.getTime()) );
        viewHolder.dateTv.setText( sdfDate.format(pickup.getTime()) );
        //TODO: Change binnerTV and statusTV to data model.
        viewHolder.binnerTv.setText("Binner Sample"); // Waiting for change in the model
        viewHolder.statusTv.setText(BinnersStatusConverter.getStatusDescription(pickup.getStatus()));

        viewHolder.setPickup(pickup);
    }

    @Override
    public int getItemCount() {
        return pickups == null ? 0 : pickups.size();
    }

    public HashSet<MapView> getMapViews() {
        return mapViews;
    }

    public List<Pickup> getPickupList() {
        return pickups;
    }
}
