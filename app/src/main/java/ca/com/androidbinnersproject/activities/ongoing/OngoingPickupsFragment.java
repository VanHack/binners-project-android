package ca.com.androidbinnersproject.activities.ongoing;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.com.androidbinnersproject.R;

/**
 * Created by Pedro Henrique on 17/05/2016.
 */
public class OngoingPickupsFragment extends Fragment {

    private static final String TAG = OngoingPickupsFragment.class.getSimpleName();

    private CardView cardView;

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
        cardView = (CardView) view.findViewById(R.id.cardViewOnGoingPickups);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cardview_ongoig_pickups, container, false);
    }
}
