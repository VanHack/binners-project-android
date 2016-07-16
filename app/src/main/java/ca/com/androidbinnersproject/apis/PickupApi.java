package ca.com.androidbinnersproject.apis;

import java.util.List;

import ca.com.androidbinnersproject.models.Pickup;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Pedro Henrique on 05/07/2016.
 */
public interface PickupApi {
    @GET("pickups")
    Call<List<Pickup>> getPickups(@Path("authentication") String authentication);

    @POST("pickup")
    public abstract Call<Pickup> createPickup(@Body Pickup pickup, @Header("Authorization") String authorization);
}