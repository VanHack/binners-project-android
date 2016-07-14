package ca.com.androidbinnersproject.apis;

import java.util.List;

import ca.com.androidbinnersproject.models.Pickup;
import retrofit.http.GET;
import retrofit2.Call;
import retrofit2.http.Path;

/**
 * Created by Pedro Henrique on 05/07/2016.
 */
public interface PickupApi {
    @GET("pickups")
    Call<List<Pickup>> getPickups(@Path("authentication") String authentication);

}