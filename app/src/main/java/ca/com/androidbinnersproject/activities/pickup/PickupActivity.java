package ca.com.androidbinnersproject.activities.pickup;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.Logger;

public class PickupActivity extends AppCompatActivity implements View.OnClickListener {

	private Toolbar mToolbar;
	private Button btnNextButton;
    private Button btnBackButton;
    private FrameLayout container;

	private List<PickupBaseFragment> mFragments;

	private int indexFragment = -1;

	private Pickup mPickupModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pickup);


		mToolbar   = (Toolbar) findViewById(R.id.activity_pickup_toolbar);
		btnNextButton = (Button) findViewById(R.id.activity_pickup_next_button);
		btnBackButton = (Button) findViewById(R.id.activity_pickup_back_button);
		container = (FrameLayout) findViewById(R.id.activity_pickup_container);

		btnNextButton.setOnClickListener(this);
		btnBackButton.setOnClickListener(this);

		initializePickupModel(getIntent().getDoubleExtra("LAT", 0), getIntent().getDoubleExtra("LON", 0));

		initializeFragments(mPickupModel);
	}

	private void initializePickupModel(double mLatitude, double mLongitude) {
		mPickupModel = new Pickup();

		double[] location = new double[]{mLatitude, mLongitude};

		mPickupModel.getAddress().getLocation().setCoordinates(location);

		/**
		 * Get Address information based on latitude and longitude
		 */
		Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
		try {
			List<Address> fromLocation = geocoder.getFromLocation(mLatitude, mLongitude, 1);
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
		mFragments.add(SelectDateFragment.newInstance(this, pickupModel));
		mFragments.add(TimePickerFragment.newInstance(this, pickupModel));
		mFragments.add(PickupBottlesFragment.newInstance(this, pickupModel));
		mFragments.add(PickupInstructionsFragment.newInstance(this, pickupModel));
		mFragments.add(PickupReviewFragment.newInstance(this, pickupModel));

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

		if (hasChanged) {
			mToolbar.setTitle(mFragments.get(indexFragment).getTitle());

			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.activity_pickup_container, mFragments.get(indexFragment));
			ft.commit();
		}
	}

	private void finishedPickUp() {
	}

	private void abortPickUp() {
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(requestCode == PickupBottlesFragment.PictureRequestCode) {

			if(resultCode != RESULT_OK) {
				Logger.Error("Failed to retrieve Picture from Camera activity or user canceled, result=" + resultCode);
				return;
			}

			Toast.makeText(this, "Picture saved in\n" + data.getData(), Toast.LENGTH_LONG).show();
		}

		super.onActivityResult(requestCode, resultCode, data);
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
					Toast.makeText(PickupActivity.this, "Cannot change!", Toast.LENGTH_SHORT).show();
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
