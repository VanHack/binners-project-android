package ca.com.androidbinnersproject.activities.pickup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.Util;

/**
 * Created by jonathan_campos on 22/05/2016.
 */
public class PickupReviewFragment extends PickupBaseFragment {

    private ImageView imgStaticMap;

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

        inicializarMapa();

        super.onViewCreated(view, savedInstanceState);
    }

    private void inicializarMapa() {
        String latEiffelTower = "48.858235";
        String lngEiffelTower = "2.294571";

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels; // Always 100%
        int heightPixels = (metrics.heightPixels * 40) / 100 ; // 40%

        StringBuilder sbMapRequestRender = new StringBuilder();
        sbMapRequestRender.append("http://maps.google.com/maps/api/staticmap?center=")
                .append(latEiffelTower)
                .append(",")
                .append(lngEiffelTower)
                .append("&zoom=15")
                .append("&size=")
                .append(widthPixels)
                .append("x")
                .append(heightPixels)
                .append("&sensor=true");

        Glide.with(getContext()).load(sbMapRequestRender.toString()).into(imgStaticMap);
    }



    @Override
    protected boolean isValid() {
        return false;
    }
}
