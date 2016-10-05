package ca.com.androidbinnersproject.activities.pickup;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.BinnersSettings;
import ca.com.androidbinnersproject.util.Logger;

/**
 * Created by jonathan_campos on 25/07/2016.
 */
public class NewPickupFragment extends Fragment implements View.OnClickListener {
    private Button btnNextButton;
    private Button btnBackButton;

    private List<PickupBaseFragment> mFragments;

    private int indexFragment = -1;

    private Pickup mPickupModel;

    private Context mContext;

    public static NewPickupFragment newInstance(Context context, final double lat, final double lon) {
        NewPickupFragment fragment = new NewPickupFragment();
        fragment.mContext = context;
        fragment.initializePickupModel(lat, lon);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_pickup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btnNextButton = (Button) view.findViewById(R.id.activity_pickup_next_button);
        btnBackButton = (Button) view.findViewById(R.id.activity_pickup_back_button);

        btnNextButton.setOnClickListener(this);
        btnBackButton.setOnClickListener(this);

        initializeFragments(mPickupModel);

        super.onViewCreated(view, savedInstanceState);
    }

    private void initializePickupModel(double lat, double lon) {
        mPickupModel = new Pickup();

        mPickupModel.setUserID(BinnersSettings.getProfileId());
        mPickupModel.getAddress().getLocation().setCoordinates(new double[]{lat, lon});

        /**
         * Get Address information based on latitude and longitude
         */
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> fromLocation = geocoder.getFromLocation(lat, lon, 1);
            for (Address address : fromLocation) {
                mPickupModel.getAddress().setStreet(address.getThoroughfare() + ", " + address.getSubThoroughfare() + " - " + address.getSubLocality());
                mPickupModel.getAddress().setCity(address.getLocality());
                mPickupModel.getAddress().setState(address.getAdminArea());
                mPickupModel.getAddress().setZip(address.getPostalCode());
            }

        } catch (IOException e) {
            Logger.Error("Error on initialize pickup model with address from Geocoder.");
        }
    }

    private void initializeFragments(Pickup pickupModel) {
        mFragments = new ArrayList<>();
        mFragments.add(SelectDateFragment.newInstance(getContext(), pickupModel));
        mFragments.add(TimePickerFragment.newInstance(getContext(), pickupModel));
        mFragments.add(PickupBottlesFragment.newInstance(getContext(), pickupModel));
        mFragments.add(PickupInstructionsFragment.newInstance(getContext(), pickupModel));
        mFragments.add(PickupReviewFragment.newInstance(getContext(), pickupModel));

        changeFragment(Action.NEXT);
    }

    private void changeFragment(Action action) {
        boolean hasChanged = false;

        if (action.ordinal() == Action.NEXT.ordinal()) {
            if (indexFragment < (mFragments.size() - 1)) {
                incrementIndex();
                hasChanged = true;
            }
        } else {
            if (indexFragment > 0) {
                decrementIndex();
                hasChanged = true;
            }
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.activity_pickup_container, mFragments.get(indexFragment));
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_pickup_back_button: {
                changeFragment(Action.BACK);
            }
            break;
            case R.id.activity_pickup_next_button: {
                PickupBaseFragment currentFragment = mFragments.get(indexFragment);

                if(currentFragment.isValid()) {
                    changeFragment(Action.NEXT);
                } else {
                    Toast.makeText(getContext(), "Cannot change!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    private void decrementIndex() {
        indexFragment--;

        if(indexFragment >= mFragments.size() -2) {
            btnNextButton.setText("Next");
        }
    }

    private void incrementIndex() {
        indexFragment++;

        if(indexFragment >= mFragments.size() -1) {
            btnNextButton.setText("Finish");
        }
    }

    public enum Action {
        BACK,
        NEXT;
    }
}
