package ca.com.androidbinnersproject.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by jonathan_campos on 26/07/2016.
 */
public interface RevalidateTokenService {
    @GET("revalidate")
    Call<String> revalidateToken(@Header("Authorization") String oldAuthorization);
}
