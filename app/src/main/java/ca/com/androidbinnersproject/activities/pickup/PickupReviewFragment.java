package ca.com.androidbinnersproject.activities.pickup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Calendar;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.Util;

/**
 * Created by jonathan_campos on 22/05/2016.
 */
public class PickupReviewFragment extends PickupBaseFragment {

    private ImageView imgStaticMap;
    private EditText edtLocation;
    private EditText edtTime;
    private EditText edtDate;
    private EditText edtInstructions;

    public static PickupBaseFragment newInstance(Context context, Pickup pickupModel) {
        PickupBaseFragment fragment = new PickupReviewFragment();
        fragment.setPickupModel(pickupModel);
        fragment.setTitle(Util.getStringResource(context, R.string.pickup_activity_title_confirm));

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pickup_review, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        imgStaticMap = (ImageView) view.findViewById(R.id.fragment_pickup_review_imgStaticMap);
        edtLocation  = (EditText) view.findViewById(R.id.fragment_pickup_review_edtLocation);
        edtTime      = (EditText) view.findViewById(R.id.fragment_pickup_review_edtTime);
        edtDate      = (EditText) view.findViewById(R.id.fragment_pickup_review_edtDate);
        edtInstructions= (EditText) view.findViewById(R.id.fragment_pickup_review_edtInstructions);

        initializeStaticMap();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        fillPickupInformation();
    }

    private void fillPickupInformation() {
        edtLocation.setText(getStreet() + " - " + getCity());
        edtTime.setText(Util.getTimeFormated(getDate()));
        edtDate.setText(Util.getDateFormated(getDate()));
        edtInstructions.setText(getInstructions());
    }

    private void initializeStaticMap() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels; // Always 100%

        StringBuilder sbMapRequestRender = new StringBuilder();
        sbMapRequestRender.append("http://maps.google.com/maps/api/staticmap?center=")
                .append(getLatitude())
                .append(",")
                .append(getLongitude())
                .append("&zoom=16")
                .append("&size=")
                .append(widthPixels)
                .append("x")
                .append(260)
                .append("&markers=color:blue%7Clabel:Pickup%7C")
                .append(getLatitude())
                .append(",")
                .append(getLongitude())
                .append("&sensor=false");

        Glide.with(getContext()).load(sbMapRequestRender.toString()).into(imgStaticMap);
    }



    @Override
    protected boolean isValid() {
        return false;
    }
}
