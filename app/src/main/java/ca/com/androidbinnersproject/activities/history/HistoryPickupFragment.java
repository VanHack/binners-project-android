package ca.com.androidbinnersproject.activities.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.MapView;

import java.util.List;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.adapters.HistoryAdapter;
import ca.com.androidbinnersproject.listeners.MainViewLoadAndRetry;
import ca.com.androidbinnersproject.models.Pickup;

/**
 * Created by jonathan_campos on 28/10/2016.
 */

public class HistoryPickupFragment extends Fragment implements MainViewLoadAndRetry<Pickup>, View.OnClickListener {

    private static final String TAG = "HistoryPickupFragment";

    private HistoryAdapter mAdapter;

    private RecyclerView rvHistoryPickup;
    private RelativeLayout rlProgress;
    private RelativeLayout rlRetry;
    private Button btnRetry;

    private HistoryPickupPresenter mPresenter;

    public static HistoryPickupFragment newInstance() {
        return new HistoryPickupFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new HistoryPickupPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_history_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvHistoryPickup = (RecyclerView) view.findViewById(R.id.rvHistoryPickup);
        rlProgress = (RelativeLayout) view.findViewById(R.id.rl_progress);
        rlRetry    = (RelativeLayout) view.findViewById(R.id.rl_retry);
        btnRetry   = (Button) view.findViewById(R.id.btnRetry);

        int rows = getResources().getInteger(R.integer.map_grid_cols);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), rows, GridLayoutManager.VERTICAL, false);
        rvHistoryPickup.setLayoutManager(layoutManager);
        rvHistoryPickup.setHasFixedSize(true);

        btnRetry.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.loadPickupsHistory();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (mAdapter != null) {
            for (MapView m : mAdapter.getMapViews()) {
                m.onLowMemory();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        if (mAdapter != null) {
            for (MapView m : mAdapter.getMapViews()) {
                m.onPause();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mAdapter != null) {
            for (MapView m : mAdapter.getMapViews()) {
                m.onDestroy();
            }
        }

        super.onDestroy();
    }

    @Override
    public void onShowProgress() {
        rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideProgress() {
        rlProgress.setVisibility(View.GONE);
    }

    @Override
    public void onShowRetry() {
        rlRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideRetry() {
        rlRetry.setVisibility(View.GONE);
    }

    @Override
    public void onRenderList(List<Pickup> obj) {
        mAdapter = new HistoryAdapter(obj);

        for (MapView m : mAdapter.getMapViews()) {
            m.onResume();
        }

        rvHistoryPickup.setAdapter(mAdapter);
    }

    @Override
    public void onErrorMessage(String message) {
        Log.e(TAG, message);
        // TODO Notify User
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRetry:
                mPresenter.loadPickupsHistory();
                break;
        }
    }
}
