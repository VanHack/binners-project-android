package ca.com.androidbinnersproject.util;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import ca.com.androidbinnersproject.apis.BaseAPI;
import ca.com.androidbinnersproject.apis.RevalidateTokenService;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by jonathan_campos on 26/07/2016.
 *
 * Class responsible to ask server to generate a new token when the old one expires.
 */
public class RevalidateToken implements Authenticator {
    private static final String TAG = "RevalidateToken";
    private static final String HEADER_AUTHORIZATION = "AUTHORIZATION";

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        String newToken = getNewToken(response.request().header(HEADER_AUTHORIZATION));

        if(newToken != null && newToken.length() > 0)
            saveNewToken(newToken);


        return response.request().newBuilder().header(HEADER_AUTHORIZATION, newToken).build();
    }

    private String getNewToken(final String oldToken) throws IOException {
        Retrofit retrofit = BaseAPI.getRetroInstance();

        RevalidateTokenService service = retrofit.create(RevalidateTokenService.class);
        Call<String> call = service.revalidateToken(oldToken);

        String newToken = call.execute().body();

        if(newToken == null) {
            Log.e(TAG, "Error on trying to generate a new token.");

            return "";
        }

        return newToken;
    }

    private void saveNewToken(String newToken) {
        BinnersSettings.setToken(newToken);
    }
}
