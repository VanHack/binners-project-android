package ca.com.androidbinnersproject.activities.onboarding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.com.androidbinnersproject.R;

/**
 * Created by jonathan_campos on 07/02/2016.
 */
public class Onboarding1Fragment extends Fragment {


    public static Onboarding1Fragment newInstance() {
        return new Onboarding1Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.onboarding_1_fragment,container,false);
    }
}
