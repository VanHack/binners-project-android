package ca.com.androidbinnersproject.activities.ongoing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.Util;

/**
 * Created by jonathan_campos on 07/09/2016.
 */
public class RatePickupDlg extends DialogFragment {

    private TextView txtTime;
    private TextView txtDate;
    private TextView txtBinnersName;
    private EditText edtComment;
    private RatingBar rtbRate;

    private Pickup mPickup;

    public static RatePickupDlg newInstance(Pickup pickup) {
        RatePickupDlg dlg = new RatePickupDlg();
        dlg.mPickup = pickup;

        return dlg;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return inflater.inflate(R.layout.rate_pickup_dialog, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        txtTime = (TextView) view.findViewById(R.id.rate_pickup_dialog_txtTime);
        txtDate = (TextView) view.findViewById(R.id.rate_pickup_dialog_txtDate);
        txtBinnersName = (TextView) view.findViewById(R.id.rate_pickup_dialog_txtBinner);
        edtComment = (EditText) view.findViewById(R.id.rate_pickup_dialog_edtComment);
        rtbRate = (RatingBar) view.findViewById(R.id.rate_pickup_dialog_rate);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);

        loadInformation();

        super.onResume();
    }

    private void loadInformation() {
        if(mPickup != null) {
            txtTime.setText(Util.getTimeFormated(mPickup.getTime()));
            txtDate.setText(Util.getDateFormated(mPickup.getTime()));
            txtBinnersName.setText("Not Working yet!");
        }
    }
}
