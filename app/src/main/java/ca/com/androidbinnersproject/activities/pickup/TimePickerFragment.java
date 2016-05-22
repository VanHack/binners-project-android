
package ca.com.androidbinnersproject.activities.pickup;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.datetimepicker.HapticFeedbackController;
import com.android.datetimepicker.Utils;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Locale;

import ca.com.androidbinnersproject.R;

public class TimePickerFragment extends Fragment implements RadialPickerLayout.OnValueSelectedListener {

	private final String TimeFormat = "HH:mm";
	private static final String AM_Text = "AM";
	private static final String PM_Text = "PM";

	public static final int HOUR_INDEX = 0;
	public static final int MINUTE_INDEX = 1;
	public static final int AMPM_INDEX = 2;
	public static final int AM = 0;
	public static final int PM = 1;

	private Calendar calendar;

	private RadialPickerLayout radialPickerLayout;
	private TextView hoursView;
	private TextView minutesView;
	private TextView ampmHitspace;
	private TextView ampmLabel;

	private HapticFeedbackController hapticFeedbackController;

	private int selectedColor;
	private int unselectedColor;

	private boolean allowAutoAdvance = true;

	public TimePickerFragment() {
	}

	public static TimePickerFragment newInstance(int hourOfDay, int minute, boolean is24HourMode) {
		Bundle args = new Bundle();

		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		calendar = Calendar.getInstance(Locale.CANADA);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.pickup_timepicker, container, false);

		selectedColor = Color.parseColor("#369a3f");
		unselectedColor = Color.parseColor("#a5c7a6");

		radialPickerLayout = (RadialPickerLayout) view.findViewById(R.id.pickup_time_picker);
		hoursView = (TextView) view.findViewById(R.id.pickup_timepicker_hours);
		minutesView = (TextView) view.findViewById(R.id.pickup_timepicker_minutes);
		ampmHitspace = (TextView) view.findViewById(R.id.pickup_timepicker_ampm_hitspace);
		ampmLabel = (TextView) view.findViewById(R.id.pickup_timepicker_ampm_label);

		hapticFeedbackController = new HapticFeedbackController(getActivity());

		radialPickerLayout.setOnValueSelectedListener(this);
		radialPickerLayout.initialize(getActivity(), hapticFeedbackController, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, 0, 12, 0, 59);

		setCurrentItemShowing(HOUR_INDEX, true, true);

		radialPickerLayout.invalidate();

		hoursView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurrentItemShowing(HOUR_INDEX, true, false);
			}
		});

		minutesView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurrentItemShowing(MINUTE_INDEX, true, false);
			}
		});

		updateAmPmDisplay(calendar.get(Calendar.HOUR_OF_DAY) < 12 ? AM : PM);

		ampmHitspace.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int amOrPm = radialPickerLayout.getIsCurrentlyAmOrPm();

				if (amOrPm == AM)
					amOrPm = PM;
				else if (amOrPm == PM)
					amOrPm = AM;

				updateAmPmDisplay(amOrPm);
				radialPickerLayout.setAmOrPm(amOrPm);
			}
		});

		return view;
	}

	private void updateAmPmDisplay(int amOrPm) {
		if (amOrPm == AM) {
			ampmLabel.setText(AM_Text);
			ampmHitspace.setContentDescription(AM_Text);
		} else if (amOrPm == PM){
			ampmLabel.setText(PM_Text);
			ampmHitspace.setContentDescription(PM_Text);
		} else {
			ampmLabel.setText("--");
		}
	}

	private void setCurrentItemShowing(int index, boolean animateCircle, boolean delayLabelAnimate) {

		radialPickerLayout.setCurrentItemShowing(index, animateCircle);

		TextView labelToAnimate;

		if(index == HOUR_INDEX)
			labelToAnimate = hoursView;
		else
			labelToAnimate = minutesView;

		int hourColor = (index == HOUR_INDEX) ? selectedColor : unselectedColor;
		int minuteColor = (index == MINUTE_INDEX) ? selectedColor : unselectedColor;

		hoursView.setTextColor(hourColor);
		minutesView.setTextColor(minuteColor);

		ObjectAnimator pulseAnimator = Utils.getPulseAnimator(labelToAnimate, 0.85f, 1.1f);

		if (delayLabelAnimate)
			pulseAnimator.setStartDelay(300);

		pulseAnimator.start();
	}

	@Override
	public void onValueSelected(int pickerIndex, int newValue, boolean autoAdvance) {

		if (pickerIndex == HOUR_INDEX) {
			if (valueRespectsHoursConstraint(newValue)) {

				setHour(newValue);

				if (allowAutoAdvance && autoAdvance)
					setCurrentItemShowing(MINUTE_INDEX, true, true);
			}
		} else if (pickerIndex == MINUTE_INDEX) {

			if (valueRespectsMinutesConstraint(newValue)) {
				setMinute(newValue);
			}

		} else if (pickerIndex == AMPM_INDEX) {

			updateAmPmDisplay(newValue);

		}
	}

	private void setHour(int value) {

		String format;

		format = "%d";
		value = value % 12;

		if (value == 0)
			value = 12;

		CharSequence text = String.format(format, value);
		hoursView.setText(text);
	}

	private void setMinute(int value) {

		if (value == 60)
			value = 0;

		CharSequence text = String.format(Locale.getDefault(), "%02d", value);

		minutesView.setText(text);
	}

	private boolean valueRespectsHoursConstraint(int value) {
		return (value >= 0 && value <= 12);
	}

	private boolean valueRespectsMinutesConstraint(int value) {

		int hour = radialPickerLayout.getHours();

		boolean checkedMinMinute = true;
		boolean checkedMaxMinute = true;

		if (hour == 0)
			checkedMinMinute = (value >= 0);

		if (hour == 12)
			checkedMaxMinute = (value <= 59);

		return checkedMinMinute && checkedMaxMinute;
	}
}
