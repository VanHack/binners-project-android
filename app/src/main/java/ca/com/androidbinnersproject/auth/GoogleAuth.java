
package ca.com.androidbinnersproject.auth;

import android.accounts.Account;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import java.io.IOException;

import ca.com.androidbinnersproject.apis.BaseAPI;
import ca.com.androidbinnersproject.apis.GoogleLoginService;
import ca.com.androidbinnersproject.auth.keys.KeyManager;
import ca.com.androidbinnersproject.listeners.OnAuthListener;
import ca.com.androidbinnersproject.models.Profile;
import ca.com.androidbinnersproject.util.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GoogleAuth extends Authentication implements OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    public static final int GOOGLE_SIGN_IN = 10;
    final String LOG_TAG = getClass().getName();

    GoogleSignInOptions mGso;
    final Profile mProfile = new Profile();

    public GoogleAuth(AppCompatActivity activity, OnAuthListener listener, KeyManager keyManager) {
        this.activity = activity;

        mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGso)
                .build();

		if(listener != null)
        	setOnAuthListener(listener);
    }

    @Override
    public void login() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    public void logout() {

    }

    @Override
    public void revoke() {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            mProfile.setName(result.getSignInAccount().getDisplayName());
            mProfile.setEmail(result.getSignInAccount().getEmail());

            AccessTokenRequest request = new AccessTokenRequest();
            request.execute(result.getSignInAccount());

        } else {
            Log.e(LOG_TAG, "Google login failure!");
            onAuthListener.onLoginError("Error on trying to log at the Google.");
        }
    }

    private class AccessTokenRequest extends AsyncTask<GoogleSignInAccount, Void, String> {
        @Override
        protected String doInBackground(GoogleSignInAccount... params) {
            GoogleSignInAccount acct = params[0];

            String scopes = "oauth2:profile email";

            Account androidAccount = new Account(mProfile.getEmail(), GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);

            String token = "";

            try {
                token = GoogleAuthUtil.getToken(activity, androidAccount, scopes);
            } catch (IOException | GoogleAuthException e ) {
                Logger.Error(e.getMessage());
            }

            return token;
        }

        @Override
        protected void onPostExecute(String token) {
            Log.i(LOG_TAG, "Google access token retrieved!");
            mProfile.setToken(token);

            Retrofit retrofit = BaseAPI.getRetroInstance();

            GoogleLoginService service = retrofit.create(GoogleLoginService.class);

            Call<Profile> call = service.authenticate(token);

            call.enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {
                    Log.i(LOG_TAG, "Backend login success!");
                    onAuthListener.onLoginSuccess(mProfile);
                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {
                    Log.e(LOG_TAG, "Backend login failure!");
                    onAuthListener.onLoginError("Error on trying to log at the backend.");
                }
            });
        }
    }
}
