package ca.com.androidbinnersproject.auth;

import android.app.Activity;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ca.com.androidbinnersproject.activities.CreateAccountActivity;
import ca.com.androidbinnersproject.apis.BaseAPI;
import ca.com.androidbinnersproject.apis.TwitterLoginService;
import ca.com.androidbinnersproject.auth.keys.ApiKey;
import ca.com.androidbinnersproject.auth.keys.KeyManager;
import ca.com.androidbinnersproject.bll.CreateAccount;
import ca.com.androidbinnersproject.listeners.OnAuthListener;
import ca.com.androidbinnersproject.listeners.ResponseListener;
import ca.com.androidbinnersproject.models.Profile;
import ca.com.androidbinnersproject.models.User;
import ca.com.androidbinnersproject.util.Logger;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TwitterAuth extends Authentication {

	public TwitterAuthClient authClient;
	private TwitterSession session;

	public TwitterAuth(Activity activity, OnAuthListener listener, KeyManager keyManager) {
		this.activity = activity;
		onAuthListener = listener;

		ApiKey twitterApiKey = keyManager.GetTwitterApiKey();

		TwitterAuthConfig authConfig = new TwitterAuthConfig(twitterApiKey.GetKey(), twitterApiKey.GetSecret());
		Fabric.with(activity, new Twitter(authConfig));
	}

	@Override
	public void login() {
		authClient = new TwitterAuthClient();
		authClient.authorize(activity, new Callback<TwitterSession>() {
			@Override
			public void success(Result<TwitterSession> result) {

				Logger.Info("Twitter login successful");
				session = result.data;
				signInBackend(true);
			}

			@Override
			public void failure(TwitterException e) {
				Logger.Info("Twitter login failed: " + e.getMessage());
			}
		});
	}

	private void signInBackend(final boolean firstTime) {

		if(session == null)
			return;

		TwitterAuthToken token = session.getAuthToken();

		Retrofit retrofit = BaseAPI.getRetroInstance();
		TwitterLoginService service = retrofit.create(TwitterLoginService.class);

		Call<Profile> call = service.authenticate(token.token, token.secret);

		call.enqueue(new retrofit2.Callback<Profile>() {
			@Override
			public void onResponse(Call<Profile> call, Response<Profile> response) {

				Logger.Info("Received Twitter.signInBackend response with code: " + response.code());

				if(response.isSuccessful()) {

					Logger.Info("Server responded with success status");

				} else {

					if(!firstTime)
					{
						Logger.Info("Failed to create account for new Twitter user, quitting to avoid StackOverflow");
						return;
					}

					try {
						JSONObject errorBody = new JSONObject(response.errorBody().string());

						if(errorBody.getJSONObject("details").get("message").toString().equalsIgnoreCase("User not found.")) {
							Logger.Info("User not found in server, will invoke CreateUser");

							CreateAccount createAccount = new CreateAccount(null);
							createAccount.newUser(session.getUserName(), "NoEmail", "_NoPassword_");

							signInBackend(false);

							return;
						}

						Logger.Error("Server responded with error status and could not determine the reason");
					} catch(IOException | JSONException e) {
						Logger.Info("Could not get errorBody from server response: " + e.getMessage());
					}

				}
			}

			@Override
			public void onFailure(Call<Profile> call, Throwable t) {
				Logger.Info("Twitter login failed");
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
