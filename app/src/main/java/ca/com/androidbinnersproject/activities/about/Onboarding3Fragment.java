package ca.com.androidbinnersproject.activities.about;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.listeners.OnSkipListener;
import ca.com.androidbinnersproject.util.Logger;

/**
 * Created by jonathan_campos on 07/02/2016.
 */
public class Onboarding3Fragment extends Fragment {

    public Onboarding3Fragment newInstance() {
        return new Onboarding3Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.onboarding_3_fragment,container,false);
    }
}
