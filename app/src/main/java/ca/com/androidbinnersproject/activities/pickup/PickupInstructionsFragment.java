package ca.com.androidbinnersproject.activities.pickup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.com.androidbinnersproject.R;

public class PickupInstructionsFragment extends Fragment {

	public PickupInstructionsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pickup_additional_notes, container, false);
	}
}
