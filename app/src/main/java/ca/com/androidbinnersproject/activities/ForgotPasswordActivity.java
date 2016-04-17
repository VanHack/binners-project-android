package ca.com.androidbinnersproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.apis.BaseAPI;
import ca.com.androidbinnersproject.apis.ForgotPasswordService;
import ca.com.androidbinnersproject.models.Profile;
import ca.com.androidbinnersproject.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgotPasswordActivity extends AppCompatActivity {

	private EditText emailField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_password);

		Button submitButton = (Button) findViewById(R.id.forgot_submit_button);
		emailField = (EditText) findViewById(R.id.forgot_email_field);

		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String email = emailField.getText().toString();

				if(email.isEmpty()) {
					Toast.makeText(ForgotPasswordActivity.this, "Please provide your email address", Toast.LENGTH_LONG).show();
					return;
				}

				if(!email.contains("@")) {
					//TODO make proper check
					Toast.makeText(ForgotPasswordActivity.this, "Please provide a valid email address", Toast.LENGTH_LONG).show();
					return;
				}

				submit(email);
			}
		});
	}

	private void submit(String email) {
		Retrofit retrofit = BaseAPI.getRetroInstance();

		ForgotPasswordService service = retrofit.create(ForgotPasswordService.class);

		Call<User> call = service.forgotPassword(email);

		Toast.makeText(ForgotPasswordActivity.this, "Sending", Toast.LENGTH_SHORT).show();
		call.enqueue(new Callback<User>() {
			@Override
			public void onResponse(Call<User> call, Response<User> response) {
				if(response.isSuccessful()) {
					Toast.makeText(ForgotPasswordActivity.this, "Request successfully sent", Toast.LENGTH_LONG).show();
					finish();
				} else {
					Toast.makeText(ForgotPasswordActivity.this, "There were a problem while sending request, please try again", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(Call<User> call, Throwable t) {
				Toast.makeText(ForgotPasswordActivity.this, "There were a problem while sending request, please try again", Toast.LENGTH_LONG).show();
			}
		});
	}
}
