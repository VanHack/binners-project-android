package ca.com.androidbinnersproject.activities.pickup;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.Calendar;

import ca.com.androidbinnersproject.models.Pickup;

/**
 * Created by jonathan_campos on 27/06/2016.
 */
public abstract class PickupBaseFragment extends Fragment {
    protected String mTitle;
    protected Pickup mPickupModel;

    protected void setTitle(final String title) {
        this.mTitle = title;
    }

    protected String getTitle() {
        return mTitle;
    }

    protected void setPickupModel(final Pickup pickupModel) {
        this.mPickupModel = pickupModel;
    }

    protected double getLatitude() {
        return mPickupModel.getPickupAddress().getLocation().getCoordinates()[0];
    }

    protected double getLongitude() {
        return mPickupModel.getPickupAddress().getLocation().getCoordinates()[1];
    }

    protected void setDate(Calendar calendar) {
        mPickupModel.setDateTime(calendar);
    }

    protected Calendar getDate() {
        return mPickupModel.getDateTime();
    }

    protected void setInstructions(final String instructions) {
        mPickupModel.setInstructions(instructions);
    }

    protected String getInstructions() {
        return mPickupModel.getInstructions();
    }

    protected String getStreet() {
        return mPickupModel.getPickupAddress().getStreet();
    }

    protected String getCity() {
        return mPickupModel.getPickupAddress().getCity();
    }

    protected abstract boolean isValid();
}
