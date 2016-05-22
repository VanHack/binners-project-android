package ca.com.androidbinnersproject.activities.pickup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.util.Logger;
import ca.com.androidbinnersproject.util.Util;

public class PickupActivity extends AppCompatActivity {

	private Toolbar mToolbar;
	private Button btnNextButton;
    private Button btnBackButton;
    private FrameLayout container;

    private double mLatitude;
    private double mLongitude;
    private int currentStage;

    public static final int stageDate = 0;
    public static final int stageTime = 1;
    public static final int stageBottles = 2;
    public static final int stageInstructions = 3;
    public static final int stageConfirm = 4;
    public static final int stageLast = 4; //value of last stage

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pickup);

		mLatitude = getIntent().getDoubleExtra("LAT", 0);
		mLongitude = getIntent().getDoubleExtra("LON", 0);

		Toast.makeText(PickupActivity.this, "Latitude: " + mLatitude + " Longitude: " + mLongitude, Toast.LENGTH_LONG).show();


        mToolbar   = (Toolbar) findViewById(R.id.activity_pickup_toolbar);
        btnNextButton = (Button) findViewById(R.id.activity_pickup_next_button);
        btnBackButton = (Button) findViewById(R.id.activity_pickup_back_button);
        container = (FrameLayout) findViewById(R.id.activity_pickup_container);

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId() == R.id.activity_pickup_next_button) {

                    if(currentStage > stageLast)
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
        btnNextButton.setOnClickListener(buttonListener);
		btnBackButton.setOnClickListener(buttonListener);
	}

    @Override
    protected void onStart() {
        super.onStart();

        currentStage = -1;
        setFragmentStage(stageDate);
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
			case stageDate:
				transaction.add(R.id.activity_pickup_container, new SelectDateFragment());
                mToolbar.setTitle(Util.getStringResource(this, R.string.pickup_activity_title_date));
			break;
			case stageTime:
				transaction.add(R.id.activity_pickup_container, new TimePickerFragment());
                mToolbar.setTitle(Util.getStringResource(this, R.string.pickup_activity_title_time));
			break;
			case stageBottles:
				transaction.add(R.id.activity_pickup_container, new PickupBottlesFragment());
                mToolbar.setTitle(Util.getStringResource(this, R.string.pickup_activity_title_bottles));
			break;
			case stageInstructions:
				transaction.add(R.id.activity_pickup_container, new PickupInstructionsFragment());
                mToolbar.setTitle(Util.getStringResource(this, R.string.pickup_activity_title_instructions));
			break;
			case stageConfirm:
				transaction.add(R.id.activity_pickup_container, new PickupReviewFragment());
				mToolbar.setTitle(Util.getStringResource(this, R.string.pickup_activity_title_confirm));
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
