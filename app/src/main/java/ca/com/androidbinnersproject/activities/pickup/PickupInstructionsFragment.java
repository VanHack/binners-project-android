package ca.com.androidbinnersproject.activities.pickup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.Util;

public class PickupInstructionsFragment extends PickupBaseFragment {

	private EditText edtAdditionalInformation;

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
		View view = inflater.inflate(R.layout.fragment_pickup_additional_notes, container, false);

		edtAdditionalInformation = (EditText) view.findViewById(R.id.pickup_additional_notes_edtInformation);

		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		edtAdditionalInformation.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if(s != null)
					setInstructions(s.toString());
			}
		});
	}

	@Override
	protected boolean isValid() {
		return true;
	}
}
