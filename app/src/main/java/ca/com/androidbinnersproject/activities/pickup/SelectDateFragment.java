package ca.com.androidbinnersproject.activities.pickup;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.Util;

/**
 * Created by jonathan_campos on 02/03/2016.
 */
public class SelectDateFragment extends PickupBaseFragment implements View.OnClickListener {

    private TextView txtDayOfWeek;
    private TextView txtDayOfMonth;
    private TextView txtMonth;

    private ImageButton btnPreviousDay;
    private ImageButton btnNextDay;
    private ImageButton btnPreviousMonth;
    private ImageButton btnNextMonth;
    private CalendarView mCalendarView;

    private String[] daysOfWeek;
    private String[] months;

    private Calendar superCalendar = Calendar.getInstance();

    private Pickup mPickup;

    public static PickupBaseFragment newInstance(Context context, Pickup pickupModel) {
        PickupBaseFragment fragment = new SelectDateFragment();
        fragment.setPickupModel(pickupModel);
        fragment.setTitle(Util.getStringResource(context, R.string.pickup_activity_title_date));

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pickup_calendar, container, false);

        txtDayOfWeek = (TextView) view.findViewById(R.id.select_date_activity_txtDayOfWeek);
        txtDayOfMonth = (TextView) view.findViewById(R.id.select_date_activity_txtDayOfMonth);
        txtMonth = (TextView) view.findViewById(R.id.select_date_activity_txtMonth);
        mCalendarView = (CalendarView) view.findViewById(R.id.select_date_activity_calendar_view);

        btnPreviousDay = (ImageButton) view.findViewById(R.id.select_date_activity_btnPreviousDay);
        btnNextDay = (ImageButton) view.findViewById(R.id.select_date_activity_btnNextDay);
        btnPreviousMonth = (ImageButton) view.findViewById(R.id.select_date_activity_btnPreviousMonth);
        btnNextMonth = (ImageButton) view.findViewById(R.id.select_date_activity_btnNextMonth);
        daysOfWeek = getResources().getStringArray(R.array.day_of_week);
        months = getResources().getStringArray(R.array.months);

        btnPreviousDay.setOnClickListener(this);
        btnNextDay.setOnClickListener(this);
        btnPreviousMonth.setOnClickListener(this);
        btnNextMonth.setOnClickListener(this);

        initializeCalendar();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateViews(superCalendar);
    }

    private void initializeCalendar() {
        ViewGroup vg = (ViewGroup) mCalendarView.getChildAt(0);

        View subView = vg.getChildAt(0);

        if(subView instanceof TextView) {
            ((TextView)subView).setVisibility(View.GONE);
        }

        mCalendarView.setShowWeekNumber(false);
        mCalendarView.setFirstDayOfWeek(2);

        setMinDate();

        setListener();
    }

    private void setListener() {
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                if (isValid(year, month, day)) {
                    superCalendar.set(year, month, day);
                    updateViews(superCalendar);

                } else {
                    calendarView.setDate(superCalendar.getTime().getTime());
                    Toast.makeText(getActivity(), "Not Allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateViews(Calendar calendar) {
        int day          = calendar.get(Calendar.DAY_OF_MONTH);
        String dayOfWeek = daysOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        String month     = months[calendar.get(Calendar.MONTH)];
        int year         = calendar.get(Calendar.YEAR);

        txtDayOfWeek.setText(dayOfWeek);
        txtDayOfMonth.setText(String.valueOf(day));
        txtMonth.setText(getFormattedMonth(month, year));
    }

    public SpannableString getFormattedMonth(String month, int year) {
        final String fullText = month + " " + year;

        SpannableString text = new SpannableString(fullText);

        text.setSpan(new ForegroundColorSpan(Color.LTGRAY), month.length(), fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return text;
    }

    public void setMinDate() {

    }

    public boolean isValid(int year, int month, int day) {
        Calendar calNow = Calendar.getInstance();
        Calendar calUser = Calendar.getInstance();
        calUser.set(year, month, day);

        return calUser.compareTo(calNow) >=0;
    }

    @Override
    public void onClick(View view) {
        Calendar calClone = (Calendar) superCalendar.clone();

        switch(view.getId()) {
            case R.id.select_date_activity_btnPreviousDay:{
                calClone.add(Calendar.DAY_OF_MONTH, -1);
            } break;
            case R.id.select_date_activity_btnNextDay:{
                calClone.add(Calendar.DAY_OF_MONTH, 1);
            } break;
            case R.id.select_date_activity_btnPreviousMonth:{
                calClone.add(Calendar.MONTH, -1);
            } break;
            case R.id.select_date_activity_btnNextMonth:{
                calClone.add(Calendar.MONTH, 1);
            } break;
        }

        if(isValid(calClone.get(Calendar.YEAR), calClone.get(Calendar.MONTH), calClone.get(Calendar.DAY_OF_MONTH))) {
            superCalendar = calClone;
            mCalendarView.setDate(superCalendar.getTime().getTime());
        } else {
            mCalendarView.setDate(superCalendar.getTime().getTime());
            Toast.makeText(getActivity(), "Not Allowed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected boolean isValid() {
        return true;
    }
}
