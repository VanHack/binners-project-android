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
public class Onboarding3Fragment extends Fragment {

    public static Onboarding3Fragment newInstance() {
        return new Onboarding3Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.onboarding_3_fragment,container,false);
    }
}
