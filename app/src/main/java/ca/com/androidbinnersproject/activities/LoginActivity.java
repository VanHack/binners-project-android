package ca.com.androidbinnersproject.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.onboarding.OnboardingActivity;
import ca.com.androidbinnersproject.auth.AppAuth;
import ca.com.androidbinnersproject.auth.Authentication;
import ca.com.androidbinnersproject.auth.FacebookAuth;
import ca.com.androidbinnersproject.auth.GoogleAuth;
import ca.com.androidbinnersproject.auth.TwitterAuth;
import ca.com.androidbinnersproject.auth.keys.KeyManager;
import ca.com.androidbinnersproject.listeners.OnAuthListener;
import ca.com.androidbinnersproject.models.Profile;
import ca.com.androidbinnersproject.util.BinnersSettings;
import ca.com.androidbinnersproject.util.Logger;
import ca.com.androidbinnersproject.util.Util;


public class LoginActivity extends AppCompatActivity implements OnAuthListener, View.OnClickListener {
	public static final int FROM_LOGIN = 25678;

	private KeyManager keyManager;

	private Authentication authentication;

	private BottomSheetBehavior sheetBehavior;


	@BindView(R.id.login_email)
  AppCompatEditText edtEmail;
	@BindView(R.id.login_password)
  AppCompatEditText edtPassword;
  @BindView(R.id.login_email_layout)
  LinearLayout loginEmailLayout;
  @BindView(R.id.login_facebook_widget)
  LoginButton fbWidgetButton;


	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
    ButterKnife.bind(this);

		keyManager = new KeyManager(getResources());

		if(!keyManager.RetrieveKeys())
			Logger.Error("Failed to retrieve keys");

		//showOnboarding();

    sheetBehavior = BottomSheetBehavior.from(loginEmailLayout);
    sheetBehavior.setHideable(true);
    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

	}

	@Override
	protected void onResume() {
		super.onResume();
		dismissPDialog();
	}

	private void showOnboarding() {
		Intent intent = new Intent(this, OnboardingActivity.class);
		startActivity(intent);
	}

	@Override
	public void onLoginSuccess(Profile profile) {
		dismissPDialog();

		saveAuthenticatedUser(profile);

		setResult(RESULT_OK);

    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);

		finish();
	}

	@Override
	public void onLoginError(String message) {
		dismissPDialog();
		Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onLoginCancel() {
		dismissPDialog();
	}

	@Override
	public void onRevoke() {
		dismissPDialog();
	}

	private void dismissPDialog() {
		if(mProgressDialog != null)
			mProgressDialog.dismiss();
	}

	private void saveAuthenticatedUser(Profile profile) {
		BinnersSettings.setToken(profile.getToken());
		BinnersSettings.setProfileName(profile.getName());
		BinnersSettings.setProfileEmail(profile.getEmail());
		BinnersSettings.setProfileId(profile.getId());
	}

  @Override
  public void onBackPressed() {
    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
  }

  @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == GoogleAuth.GOOGLE_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			((GoogleAuth) authentication).handleSignInResult(result);
		} else if(authentication instanceof FacebookAuth) {
			if(resultCode == RESULT_OK)
				((FacebookAuth) authentication).getFacebookCallbackManager().onActivityResult(requestCode, resultCode, data);
		} else if(authentication instanceof TwitterAuth) {
			((TwitterAuth) authentication).authClient.onActivityResult(requestCode, resultCode, data);
		}

		if(requestCode == CreateAccountActivity.CREATE_ACCOUNT_RESULT) {
			if(resultCode == RESULT_OK) {
				//String token = data.getStringExtra("TOKEN");
				Profile profile = (Profile) data.getSerializableExtra("PROFILE");

				onLoginSuccess(profile);
			}
		}
	}

	public boolean isEditFilled() {
		return edtEmail.getText().toString().length() > 0 && edtPassword.getText().toString().length() > 0;
	}

	@OnClick({R.id.login_login_button, R.id.login_signin_button, R.id.login_forgot_button, R.id.login_facebook_button})
	public void onClick(View view) {

		if(!Util.hasInternetConnection(LoginActivity.this)) {
			Toast.makeText(LoginActivity.this, R.string.has_no_connection, Toast.LENGTH_SHORT).show();
			return;
		}

		switch(view.getId()) {

			case R.id.login_facebook_button:
        fbWidgetButton.performClick();
				if(authentication == null || !(authentication instanceof FacebookAuth))
					authentication = new FacebookAuth(LoginActivity.this, LoginActivity.this, keyManager);
			break;

//			case R.id.login_button_twitter:
//				if(authentication == null || !(authentication instanceof TwitterAuth))
//					authentication = new TwitterAuth(LoginActivity.this, LoginActivity.this, keyManager);
//			break;
//
//			case R.id.login_button_google:
//				if(authentication == null || !(authentication instanceof GoogleAuth))
//					authentication = new GoogleAuth(LoginActivity.this, LoginActivity.this, keyManager);
//			break;

      case R.id.login_signin_button:
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        break;

			case R.id.login_login_button:
        mProgressDialog = ProgressDialog.show(LoginActivity.this, "Login", getString(R.string.executing_sign_in));

        if(isEditFilled()) {
					if(Util.isEmailValid(edtEmail.getText().toString())) {
						authentication = new AppAuth(LoginActivity.this, edtEmail.getText().toString(), edtPassword.getText().toString(), LoginActivity.this);
            authentication.login();
          } else {
						dismissPDialog();
						Toast.makeText(LoginActivity.this, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
					}
				} else {
					dismissPDialog();
					Toast.makeText(LoginActivity.this, getString(R.string.fill_login), Toast.LENGTH_SHORT).show();

					return;
				}
				break;
		}


	}
}
