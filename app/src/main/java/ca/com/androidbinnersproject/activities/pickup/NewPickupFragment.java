package ca.com.androidbinnersproject.activities.pickup;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
public class NewPickupFragment extends Fragment {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_pickup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

        if (action.ordinal() == Action.NEXT.ordinal()) {
            if (indexFragment < (mFragments.size() - 1))
                incrementIndex();
        } else {
            if (indexFragment > 0)
                decrementIndex();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.activity_pickup_container, mFragments.get(indexFragment));
        ft.commit();
    }

    private void decrementIndex() {
        indexFragment--;
    }

    private void incrementIndex() {
        indexFragment++;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.newpickup_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newpickup_menu_next:
                PickupBaseFragment currentFragment = mFragments.get(indexFragment);

                if(currentFragment.isValid()) {
                    changeFragment(Action.NEXT);
                } else {
                    Toast.makeText(getContext(), "Cannot change!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.newpickup_menu_back:
                changeFragment(Action.BACK);
                return true;
        }

        return false;
    }

    public enum Action {
        BACK,
        NEXT;
    }
}
