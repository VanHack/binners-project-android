package ca.com.androidbinnersproject.bll;

import android.util.Log;

import ca.com.androidbinnersproject.apis.BaseAPI;
import ca.com.androidbinnersproject.apis.CreateUserService;
import ca.com.androidbinnersproject.listeners.ResponseListener;
import ca.com.androidbinnersproject.models.Profile;
import ca.com.androidbinnersproject.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jonathan_campos on 21/02/2016.
 */
public class CreateAccount {
	final String LOG_TAG = getClass().getName();

	private ResponseListener<Profile> mResponseListener;

	public CreateAccount(ResponseListener<Profile> listener) {
		mResponseListener = listener;
	}

	public void newUser(String name, String email, String password) {
		User user = new User(name, email, password);

		sendToServer(user);
	}

	private void sendToServer(User user) {

		if(mResponseListener == null) {
            Log.i(LOG_TAG, "Received null ResponseListener in sendToServer call, maybe harmless");
		}

		Retrofit retrofit = BaseAPI.getRetroInstance();

		CreateUserService service = retrofit.create(CreateUserService.class);

		Call<Profile> call = service.create(user);

		call.enqueue(new Callback<Profile>() {
			@Override
			public void onResponse(Call<Profile> call, Response<Profile> response) {
				if(response.code() == 200) {
					Log.i(LOG_TAG, "User created!");

					if(mResponseListener != null)
						mResponseListener.onSuccess(response.body());
				} else {
					Log.i(LOG_TAG, "Error: " + response.errorBody().toString());

					if(mResponseListener != null)
						mResponseListener.onFailed(response.errorBody().toString());
				}
			}

			@Override
			public void onFailure(Call<Profile> call, Throwable t) {
				if(mResponseListener != null)
					mResponseListener.onFailed("Error, try again.");
			}
		});
	}
}
