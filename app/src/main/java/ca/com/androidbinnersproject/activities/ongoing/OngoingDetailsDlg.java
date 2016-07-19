package ca.com.androidbinnersproject.activities.ongoing;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import ca.com.androidbinnersproject.R;

/**
 * Created by jonathan_campos on 18/07/2016.
 */
public class OngoingDetailsDlg extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cardview_ongoig_pickup_detail, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);

        super.onResume();
    }
}
