package ca.com.androidbinnersproject.apis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jonathan_campos on 28/01/2016.
 */
public class BaseAPI {
    private static final String ENDPOINT = "http://binners.herokuapp.com/api/v1.0/";

    private static Retrofit retrofit;

    public static Retrofit getRetroInstance() {
        if(retrofit != null) {
            return retrofit;
        }

        return retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
