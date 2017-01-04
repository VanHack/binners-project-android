package ca.com.androidbinnersproject.activities.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.CreateAccountActivity;
import ca.com.androidbinnersproject.activities.login.LoginActivity;
import ca.com.androidbinnersproject.auth.Authentication;
import ca.com.androidbinnersproject.auth.FacebookAuth;
import ca.com.androidbinnersproject.auth.GoogleAuth;
import ca.com.androidbinnersproject.auth.TwitterAuth;
import ca.com.androidbinnersproject.auth.keys.KeyManager;
import ca.com.androidbinnersproject.listeners.OnAuthListener;
import ca.com.androidbinnersproject.models.Profile;
import ca.com.androidbinnersproject.util.BinnersSettings;

/**
 * Created by leodeleon on 20/12/2016.
 */

public class LandingActivity extends AppCompatActivity implements OnAuthListener{

  @BindView(R.id.login_logo_layout) LinearLayout mLogoLayout;

  private KeyManager keyManager;
  private Authentication authentication;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);
    ButterKnife.bind(this);

    AnimationListener.Stop stopListener = new AnimationListener.Stop() {
      @Override
      public void onStop() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.nothing);
        transaction.add(R.id.login_container, new LoginFragment()).commit();
      }
    };

    mLogoLayout.setAlpha(0);
    ViewAnimator.animate(mLogoLayout)
      .alpha(0,1)
      .duration(500)
      .translationY(0, -300)
      .duration(1000)
      .onStop(stopListener).start();


  }

  public void showEmailLoginFragment() {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.nothing, R.anim.fade_out);
    transaction.add(R.id.login_container, new EmailLoginFragment()).addToBackStack(null).commit();

  }

  public void showForgotPasswordFragment() {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.nothing, R.anim.fade_out);
    transaction.add(R.id.login_container, new ForgotPasswordFragment()).addToBackStack(null).commit();
  }

  public void showCreateAccountFragment() {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.nothing, R.anim.fade_out);
    transaction.add(R.id.login_container, new CreateAccountFragment()).addToBackStack(null).commit();
  }


  @Override
  public void onBackPressed() {
    int count = getFragmentManager().getBackStackEntryCount();
    if (count == 0) {
      super.onBackPressed();
    } else {
      getFragmentManager().popBackStack();
    }
  }

  @Override
  public void onLoginSuccess(Profile profile) {

    saveAuthenticatedUser(profile);

    setResult(RESULT_OK);

    finish();
  }

  @Override
  public void onLoginError(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onLoginCancel() {
  }

  @Override
  public void onRevoke() {
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

  private void saveAuthenticatedUser(Profile profile) {
    BinnersSettings.setToken(profile.getToken());
    BinnersSettings.setProfileName(profile.getName());
    BinnersSettings.setProfileEmail(profile.getEmail());
    BinnersSettings.setProfileId(profile.getId());
  }

  private static boolean isValidEmail(String email) {
    return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  public boolean validateName(TextInputEditText editText, TextInputLayout textInputLayout) {
    if (editText.getText().toString().trim().isEmpty()) {
      textInputLayout.setError(getString(R.string.validation_name));
      editText.requestFocus();
      return false;
    } else {
      textInputLayout.setErrorEnabled(false);
    }

    return true;
  }

  public boolean validateEmail(TextInputEditText editText, TextInputLayout textInputLayout) {
    String email = editText.getText().toString().trim();

    if (email.isEmpty() || !isValidEmail(email)) {
      textInputLayout.setError(getString(R.string.invalid_email));
      editText.requestFocus();
      return false;
    } else {
      textInputLayout.setErrorEnabled(false);
    }

    return true;
  }

  public boolean validatePassword(TextInputEditText editText, TextInputLayout textInputLayout) {
    if (editText.getText().toString().trim().isEmpty() || editText.getText().length() < 5) {
      textInputLayout.setError(getString(R.string.validation_password));
      editText.requestFocus();
      return false;
    } else {
      textInputLayout.setErrorEnabled(false);
    }

    return true;
  }

}
