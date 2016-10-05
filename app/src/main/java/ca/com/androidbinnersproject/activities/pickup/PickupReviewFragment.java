package ca.com.androidbinnersproject.activities.pickup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.apis.BaseAPI;
import ca.com.androidbinnersproject.apis.PickupService;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.BinnersSettings;
import ca.com.androidbinnersproject.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jonathan_campos on 22/05/2016.
 */
public class PickupReviewFragment extends PickupBaseFragment implements View.OnClickListener, OnMapReadyCallback{

    private static final String TAG = "PickupReviewFragment";

    private EditText edtLocation;
    private EditText edtTime;
    private EditText edtDate;
    private EditText edtCanBottles;
    private EditText edtInstructions;
    private Button btnFinish;

    private GoogleMap mGoogleMap;
    private MapView mMapView;

    public static PickupBaseFragment newInstance(Context context, Pickup pickupModel) {
        PickupBaseFragment fragment = new PickupReviewFragment();
        fragment.setPickupModel(pickupModel);
        fragment.setTitle(Util.getStringResource(context, R.string.pickup_activity_title_confirm));

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pickup_review, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mMapView = (MapView) view.findViewById(R.id.fragment_pickup_review_imgStaticMap);
        edtLocation  = (EditText) view.findViewById(R.id.fragment_pickup_review_edtLocation);
        edtTime      = (EditText) view.findViewById(R.id.fragment_pickup_review_edtTime);
        edtDate      = (EditText) view.findViewById(R.id.fragment_pickup_review_edtDate);
        edtCanBottles= (EditText) view.findViewById(R.id.fragment_pickup_review_edtCanBottles);
        edtInstructions= (EditText) view.findViewById(R.id.fragment_pickup_review_edtInstructions);
        btnFinish    = (Button) view.findViewById(R.id.fragment_pickup_review_btnFinishPickup);

        btnFinish.setOnClickListener(this);

        this.mMapView.onCreate(null);
        this.mMapView.getMapAsync(this);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        fillPickupInformation();
    }

    private void fillPickupInformation() {
        edtLocation.setText(String.format("%s - %s", getStreet(), getCity()));
        edtTime.setText(Util.getTimeFormated(getDate()));
        edtDate.setText(Util.getDateFormated(getDate()));
        edtCanBottles.setText(getItems());
        edtInstructions.setText(getInstructions());
    }

    private void initializeStaticMap() {
        if (getLatitude() != 0 && getLongitude() != 0) {
            LatLng latLng;
            latLng = new LatLng(getLatitude(), getLongitude());

            this.mGoogleMap.addMarker(new MarkerOptions().position(latLng));

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
            this.mGoogleMap.moveCamera(cameraUpdate);
        }
    }



    @Override
    protected boolean isValid() {
        return false;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.fragment_pickup_review_btnFinishPickup) {
            Retrofit retrofit = BaseAPI.getRetroInstance();

            PickupService service = retrofit.create(PickupService.class);
            Call<Pickup> call = service.createPickup(mPickupModel, BinnersSettings.getToken());

            Gson gson = new Gson();
            String json = gson.toJson(mPickupModel);

            call.enqueue(new Callback<Pickup>() {
                @Override
                public void onResponse(Call<Pickup> call, Response<Pickup> response) {
                    Util.dismissProgressDialog();

                    getActivity().onBackPressed();
                }

                @Override
                public void onFailure(Call<Pickup> call, Throwable t) {
                    Util.dismissProgressDialog();

                    Log.e(TAG, "Error finishing pickup!");

                }
            });

            Util.showProgressDialog(getContext(),
                                    Util.getStringResource(getContext(), R.string.new_pickup_progress_title),
                                    Util.getStringResource(getContext(), R.string.new_pickup_progress_message));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

        MapsInitializer.initialize(getContext());
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        initializeStaticMap();

    }
}
