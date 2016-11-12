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
public class AboutPageOne extends Fragment {

    private TextView txtSkip;
    private OnSkipListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            Activity activity = (Activity) context;
            mListener = (OnSkipListener) activity;
        } catch (ClassCastException e) {
            Logger.Error("Activity AboutAppActivity must implement OnSkipListener interface.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.onboarding_1,container,false);

        txtSkip = (TextView) view.findViewById(R.id.imgSkipButton);

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick();
                } else {
                    Logger.Info("There's a problem with the AboutAppActivity's listener");
                }
            }
        });

        return view;
    }
}
