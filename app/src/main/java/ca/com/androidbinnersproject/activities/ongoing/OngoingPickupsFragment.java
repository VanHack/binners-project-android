package ca.com.androidbinnersproject.activities.ongoing;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.adapters.OngoingAdapter;
import ca.com.androidbinnersproject.apis.PickupService;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.Util;

/**
 * Created by Pedro Henrique on 17/05/2016.
 */
public class OngoingPickupsFragment extends Fragment {

    private static final String TAG = OngoingPickupsFragment.class.getSimpleName();

    private CardView cardView;
    protected OngoingAdapter ongoingAdapter;
    protected RecyclerView recyclerView;


    public static OngoingPickupsFragment newInstance() {
        OngoingPickupsFragment fragment = new OngoingPickupsFragment();
        return fragment;
    }

    public OngoingPickupsFragment() {
        // Construtor vazio requerido
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private OngoingAdapter createAdapter() {
        PickupService service = new PickupService();
        Util.getUserLogged(getActivity());
        ArrayList<Pickup> pickups = service.getPickupsDataTest();

        OngoingAdapter adapter = new OngoingAdapter(pickups);

        return adapter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_ongoing_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewOngoigPickups);
        int rows = getResources().getInteger(R.integer.map_grid_cols);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), rows, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        cardView = (CardView) v.findViewById(R.id.cardViewOnGoingPickups);

        this.ongoingAdapter = createAdapter();

        recyclerView.setHasFixedSize(true);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

        if (resultCode == ConnectionResult.SUCCESS) {
            recyclerView.setAdapter(ongoingAdapter);
        } else {
            GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), 1).show();
        }

        if (ongoingAdapter != null) {
            for (MapView m : ongoingAdapter.getMapViews()) {
                m.onResume();
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (ongoingAdapter != null) {
            for (MapView m : ongoingAdapter.getMapViews()) {
                m.onLowMemory();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        if (ongoingAdapter != null) {
            for (MapView m : ongoingAdapter.getMapViews()) {
                m.onPause();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (ongoingAdapter != null) {
            for (MapView m : ongoingAdapter.getMapViews()) {
                m.onDestroy();
            }
        }

        super.onDestroy();
    }
}
