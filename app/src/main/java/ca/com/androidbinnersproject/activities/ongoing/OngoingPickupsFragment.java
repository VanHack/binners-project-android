package ca.com.androidbinnersproject.activities.ongoing;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import ca.com.androidbinnersproject.util.RecyclerTouchListener;

/**
 * Created by Pedro Henrique on 17/05/2016.
 */
public class OngoingPickupsFragment extends Fragment implements OngoingBll.OngoingListener {

    private static final String TAG = OngoingPickupsFragment.class.getSimpleName();
    protected OngoingAdapter ongoingAdapter;
    protected RecyclerView recyclerView;
    private OngoingBll service;

    private FragmentManager fm;

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

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewOngoigPickups);

        int rows = getResources().getInteger(R.integer.map_grid_cols);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), rows, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Pickup pickup = ongoingAdapter.getPickupList().get(position);

                DialogFragment dlg = OngoingDetailsDlg.newInstance(pickup);
                dlg.show(fm, "");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        initializeFragmentManager();
    }

    private void initializeFragmentManager() {
        if(fm == null)
            fm = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_ongoing_list, container, false);
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
