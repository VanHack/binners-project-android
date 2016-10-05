package ca.com.androidbinnersproject.auth;

import android.content.Context;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.apis.AppLoginService;
import ca.com.androidbinnersproject.apis.BaseAPI;
import ca.com.androidbinnersproject.listeners.OnAuthListener;
import ca.com.androidbinnersproject.models.Profile;
import ca.com.androidbinnersproject.models.User;
import ca.com.androidbinnersproject.util.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jonathan_campos on 18/01/2016.
 */
public class AppAuth extends Authentication {

    private Context mContext;
    private User user;

    public AppAuth(final Context context, String email, String password, OnAuthListener listener) {
        mContext = context;
        user = new User();
        user.setEmail(email);
        user.setPassword(password);

        if(listener != null) {
            setOnAuthListener(listener);
        }
    }

    @Override
    public void login() {
        Retrofit retrofit = BaseAPI.getRetroInstance();

        AppLoginService service = retrofit.create(AppLoginService.class);

        Call<Profile> call = service.authenticate(user);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Logger.Info("Backend login success!");

                if(response.code() == 200) {
                    onAuthListener.onLoginSuccess(response.body());
                } else {
                    onAuthListener.onLoginError(mContext.getString(R.string.login_error));
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Logger.Error("Backend login failure!");
                onAuthListener.onLoginError(mContext.getString(R.string.login_invalid));
            }
        });
    }

    @Override
    public void logout() {

    }

    @Override
    public void revoke() {

    }
}
