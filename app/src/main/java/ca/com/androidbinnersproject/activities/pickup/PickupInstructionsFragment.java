package ca.com.androidbinnersproject.activities.pickup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.Util;

public class PickupInstructionsFragment extends PickupBaseFragment {

	public PickupInstructionsFragment() {
	}

	public static PickupBaseFragment newInstance(Context context, Pickup pickupModel) {
		PickupBaseFragment fragment = new PickupInstructionsFragment();
		fragment.setPickupModel(pickupModel);
		fragment.setTitle(Util.getStringResource(context, R.string.pickup_activity_title_instructions));

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pickup_additional_notes, container, false);
	}

	@Override
	protected boolean isValid() {
		return true;
	}
}
