package ca.com.androidbinnersproject.activities.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.CreateAccountActivity;
import ca.com.androidbinnersproject.activities.ForgotPasswordActivity;
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


  private Button btnCreateAccount;
  private Button btnForgotPassword;

  private EditText edtEmail;
  private EditText edtPassword;

  private ProgressDialog mProgressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);


    edtEmail = (EditText) findViewById(R.id.login_email);
    edtPassword = (EditText) findViewById(R.id.login_password);

    Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/oxygen.otf");

    keyManager = new KeyManager(getResources());

    if (!keyManager.RetrieveKeys())
      Logger.Error("Failed to retrieve keys");

    showOnboarding();
  }

  @Override
  protected void onResume() {
    super.onResume();
    dismissPDialog();
  }

  /**
   * The intent OnboardingActivity will be shown before the login UI
   */
  private void showOnboarding() {
    Intent intent = new Intent(this, OnboardingActivity.class);
    startActivity(intent);
  }


  @Override
  public void onLoginSuccess(Profile profile) {
    dismissPDialog();

    saveAuthenticatedUser(profile);

    setResult(RESULT_OK);

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
    if (mProgressDialog != null)
      mProgressDialog.dismiss();
  }

  private void saveAuthenticatedUser(Profile profile) {
    BinnersSettings.setToken(profile.getToken());
    BinnersSettings.setProfileName(profile.getName());
    BinnersSettings.setProfileEmail(profile.getEmail());
    BinnersSettings.setProfileId(profile.getId());
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == GoogleAuth.GOOGLE_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      ((GoogleAuth) authentication).handleSignInResult(result);
    } else if (authentication instanceof FacebookAuth) {
      if (resultCode == RESULT_OK)
        ((FacebookAuth) authentication).getFacebookCallbackManager().onActivityResult(requestCode, resultCode, data);
    } else if (authentication instanceof TwitterAuth) {
      ((TwitterAuth) authentication).authClient.onActivityResult(requestCode, resultCode, data);
    }

    if (requestCode == CreateAccountActivity.CREATE_ACCOUNT_RESULT) {
      if (resultCode == RESULT_OK) {
        //String token = data.getStringExtra("TOKEN");
        Profile profile = (Profile) data.getSerializableExtra("PROFILE");

        onLoginSuccess(profile);
      }
    }
  }

  public boolean isEditFilled() {
    return edtEmail.getText().toString().length() > 0 && edtPassword.getText().toString().length() > 0;
  }

  @Override
  public void onClick(View view) {

    if (!Util.hasInternetConnection(LoginActivity.this)) {
      Toast.makeText(LoginActivity.this, R.string.has_no_connection, Toast.LENGTH_SHORT).show();
      return;
    }

    mProgressDialog = ProgressDialog.show(LoginActivity.this, "Login", this.getString(R.string.executing_sign_in));

    switch (view.getId()) {

			/*
      case R.id.login_button_fb:
				if(authentication == null || !(authentication instanceof FacebookAuth))
					authentication = new FacebookAuth(LoginActivity.this, LoginActivity.this, keyManager);
			break;

			case R.id.login_button_twitter:
				if(authentication == null || !(authentication instanceof TwitterAuth))
					authentication = new TwitterAuth(LoginActivity.this, LoginActivity.this, keyManager);
			break;

			case R.id.login_button_google:
				if(authentication == null || !(authentication instanceof GoogleAuth))
					authentication = new GoogleAuth(LoginActivity.this, LoginActivity.this, keyManager);
			break;
			*/

      case R.id.login_button:
        if (isEditFilled()) {
          if (Util.isEmailValid(edtEmail.getText().toString())) {
            authentication = new AppAuth(LoginActivity.this, edtEmail.getText().toString(), edtPassword.getText().toString(), LoginActivity.this);
          } else {
            dismissPDialog();
            Toast.makeText(LoginActivity.this, getApplicationContext().getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
          }
        } else {
          dismissPDialog();
          Toast.makeText(LoginActivity.this, getApplicationContext().getString(R.string.fill_login), Toast.LENGTH_SHORT).show();

          return;
        }
        break;
    }
  }
}
