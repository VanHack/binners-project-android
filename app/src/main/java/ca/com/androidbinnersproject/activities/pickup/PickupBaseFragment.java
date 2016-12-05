package ca.com.androidbinnersproject.activities.pickup;

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
    return mPickupModel.getAddress().getLocation().getLatitude();
  }

  protected double getLongitude() {
    return mPickupModel.getAddress().getLocation().getLongitude();
  }

  protected void setDate(Calendar dateTime) {
    mPickupModel.setDateTime(dateTime);
  }

  protected Calendar getDate() {
    return mPickupModel.getDateTime();
  }

  protected String getItems() {
    return mPickupModel.getItems().get(0).getQuantity();
  }

  protected void setInstructions(final String instructions) {
    mPickupModel.setInstructions(instructions);
  }

  protected String getInstructions() {
    return mPickupModel.getInstructions();
  }

  protected String getStreet() {
    return mPickupModel.getAddress().getStreet();
  }

  protected abstract boolean isValid();
}
