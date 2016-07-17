package ca.com.androidbinnersproject.bll;

import android.content.Context;

import java.util.List;

import ca.com.androidbinnersproject.apis.BaseAPI;
import ca.com.androidbinnersproject.apis.PickupService;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jonathan_campos on 17/07/2016.
 */
public class OngoingBll {
    private Context mContext;
    private OngoingListener mListener;

    public OngoingBll() {
    }

    public OngoingBll(Context context, OngoingListener listener) {
        mContext   = context;
        mListener = listener;
    }

    public void getPickups(String token){
        Util.showProgressDialog(mContext, "Ongoing", "Retrieving Pick-Ups");

        Retrofit retrofit = BaseAPI.getRetroInstance();

        PickupService service = retrofit.create(PickupService.class);
        Call<List<Pickup>> call = service.getPickups(token);

        call.enqueue(new Callback<List<Pickup>>() {
            @Override
            public void onResponse(Call<List<Pickup>> call, Response<List<Pickup>> response) {
                Util.dismissProgressDialog();

                mListener.onSucessRetrievingList(response.body());
            }

            @Override
            public void onFailure(Call<List<Pickup>> call, Throwable t) {
                Util.dismissProgressDialog();

                mListener.onErrorRetrievingList("Error");
            }
        });
    }

    public interface OngoingListener {
        void onSucessRetrievingList(List<Pickup> pickupList);
        void onErrorRetrievingList(String message);
    }
}
