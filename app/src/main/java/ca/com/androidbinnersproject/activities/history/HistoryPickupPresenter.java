package ca.com.androidbinnersproject.activities.history;

import java.util.List;

import ca.com.androidbinnersproject.apis.BaseAPI;
import ca.com.androidbinnersproject.apis.PickupService;
import ca.com.androidbinnersproject.listeners.MainViewLoadAndRetry;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.BinnersSettings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jonathan_campos on 28/10/2016.
 */

public class HistoryPickupPresenter {

    private MainViewLoadAndRetry mMainView;

    public HistoryPickupPresenter(MainViewLoadAndRetry view) {
        mMainView = view;
    }

    public void loadPickupsHistory() {
        mMainView.onShowProgress();
        mMainView.onHideRetry();

        Retrofit retrofit = BaseAPI.getRetroInstance();

        final PickupService service = retrofit.create(PickupService.class);

        Call<List<Pickup>> call = service.getHistory(BinnersSettings.getToken());

        call.enqueue(new Callback<List<Pickup>>() {
            @Override
            public void onResponse(Call<List<Pickup>> call, Response<List<Pickup>> response) {
                mMainView.onRenderList(response.body());
                mMainView.onHideProgress();
            }

            @Override
            public void onFailure(Call<List<Pickup>> call, Throwable t) {
                mMainView.onHideProgress();
                mMainView.onShowRetry();
                mMainView.onErrorMessage("");
            }
        });
    }
}
