package ca.com.androidbinnersproject.activities.pickup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.BinnersSettings;

public class PickupActivity extends AppIntro2 {
  public static final String LATITUDE = "Latitude";
  public static final String LONGITUDE = "Longitude";
  private Pickup mPickup;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    double latitude = getIntent().getDoubleExtra(LATITUDE, 0D);
    double longitude = getIntent().getDoubleExtra(LONGITUDE, 0D);

    mPickup = new Pickup();
    mPickup.setUserID(BinnersSettings.getProfileId());
    mPickup.getAddress().getLocation().setCoordinates(latitude, longitude);

    addSlide(SelectDateFragment.newInstance(this, mPickup));
    addSlide(TimePickerFragment.newInstance(this, mPickup));
    addSlide(PickupBottlesFragment.newInstance(this, mPickup));
    addSlide(PickupInstructionsFragment.newInstance(this, mPickup));
    addSlide(PickupReviewFragment.newInstance(this, mPickup));

    showSkipButton(true);
    setImageSkipButton(ContextCompat.getDrawable(this, R.drawable.ic_close));
  }

  public static void startNewPickup(Context context, double latitude, double longitude) {
    Intent intent = new Intent(context, PickupActivity.class);
    intent.putExtra(LATITUDE, latitude);
    intent.putExtra(LONGITUDE, longitude);
    context.startActivity(intent);
  }

  @Override
  public void onSkipPressed() {
    super.onSkipPressed();
    finish();
  }

  @Override
  public void onDonePressed() {
    super.onDonePressed();
    finish();
    //Todo post pickup to the backend
  }
}
