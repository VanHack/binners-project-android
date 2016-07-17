package ca.com.androidbinnersproject.activities.ongoing;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;

import java.util.List;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.adapters.OngoingAdapter;
import ca.com.androidbinnersproject.bll.OngoingBll;
import ca.com.androidbinnersproject.models.Pickup;

/**
 * Created by Pedro Henrique on 17/05/2016.
 */
public class OngoingPickupsFragment extends Fragment implements OngoingBll.OngoingListener {

    private static final String TAG = OngoingPickupsFragment.class.getSimpleName();

    private CardView cardView;
    protected OngoingAdapter ongoingAdapter;
    protected RecyclerView recyclerView;
    private OngoingBll service;


    public static OngoingPickupsFragment newInstance(Context context) {
        OngoingPickupsFragment fragment = new OngoingPickupsFragment();
        fragment.service = new OngoingBll(context, fragment);
        return fragment;
    }

    public OngoingPickupsFragment() {
        // Construtor vazio requerido
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        recyclerView.setHasFixedSize(true);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        service.getPickups("AUTHORIZATION_GOES_HERE");
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

    @Override
    public void onSucessRetrievingList(List<Pickup> pickupList) {
        ongoingAdapter = new OngoingAdapter(pickupList);
        ongoingAdapter.notifyDataSetChanged();

        if (ongoingAdapter != null) {
            for (MapView m : ongoingAdapter.getMapViews()) {
                m.onResume();
            }
        }

        recyclerView.setAdapter(ongoingAdapter);
    }

    @Override
    public void onErrorRetrievingList(String message) {
        String message1 = message;
    }
}
