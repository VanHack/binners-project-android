package ca.com.androidbinnersproject.activities.pickup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import ca.com.androidbinnersproject.R;

/**
 * Created by jonathan_campos on 22/05/2016.
 */
public class PickupReviewFragment extends Fragment {

    private ImageView imgStaticMap;

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
        String url = "http://maps.google.com/maps/api/staticmap?center=" + latEiffelTower + "," + lngEiffelTower + "&zoom=15&size=200x200&sensor=true";

        Glide.with(getContext()).load(url).into(imgStaticMap);
    }
}
