package ca.com.androidbinnersproject.activities.signin;

import android.content.Context;
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

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.com.androidbinnersproject.R;

/**
 * Created by leodeleon on 20/12/2016.
 */

public class LandingActivity extends AppCompatActivity {

  @BindView(R.id.login_logo_layout) LinearLayout mLogoLayout;

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
