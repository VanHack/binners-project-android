package ca.com.androidbinnersproject.activities.pickup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.util.Logger;

public class PickupActivity extends AppCompatActivity {

    private double mLatitude;
    private double mLongitude;

    public static final int	Stage_Date = 0;
	public static final int	Stage_Time = 1;
	public static final int	Stage_Location = 2;
	public static final int	Stage_Bottles = 3;
	public static final int	Stage_Instructions = 4;
	public static final int	Stage_Confirm = 5;
	public static final int Stage_Last = 5; //value of last stage

	private int currentStage;

	private Button nextButton;
	private Button backButton;
	private FrameLayout container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pickup);

		mLatitude = getIntent().getDoubleExtra("LAT", 0);
		mLongitude = getIntent().getDoubleExtra("LON", 0);

		Toast.makeText(PickupActivity.this, "Latitude: " + mLatitude + " Longitude: " + mLongitude, Toast.LENGTH_LONG).show();

		currentStage = -1;
		setFragmentStage(Stage_Date);

		nextButton = (Button) findViewById(R.id.pickup_next_button);
		backButton = (Button) findViewById(R.id.pickup_back_button);
		container = (FrameLayout) findViewById(R.id.pickup_container);

		View.OnClickListener buttonListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			if(v.getId() == R.id.pickup_next_button) {

				if(currentStage > Stage_Last)
					finishedPickUp();
				else
					setFragmentStage(currentStage + 1);

			} else {

				if(currentStage <= 0)
					abortPickUp();
				else
					setFragmentStage(currentStage - 1);

			}
			}
		};

		nextButton.setOnClickListener(buttonListener);
		backButton.setOnClickListener(buttonListener);
	}

	private void setFragmentStage(int stage) {

		if(currentStage == stage)
			return;

		currentStage = stage;

		if(container != null && container.getChildCount() > 0)
			container.removeAllViews();

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		//TODO cache fragment state to avoid state loss between stage switches
		switch(currentStage) {
			case Stage_Date:
				transaction.add(R.id.pickup_container, new SelectDateFragment());
			break;

			case Stage_Time:
				transaction.add(R.id.pickup_container, new TimePickerFragment());
			break;

			case Stage_Location:
			break;

			case Stage_Bottles:
				transaction.add(R.id.pickup_container, new PickupBottlesFragment());
			break;

			case Stage_Instructions:
				transaction.add(R.id.pickup_container, new PickupInstructionsFragment());
			break;

			case Stage_Confirm:
			break;
		}

		transaction.commit();
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
}
