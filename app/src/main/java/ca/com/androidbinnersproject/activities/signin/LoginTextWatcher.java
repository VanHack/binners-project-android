package ca.com.androidbinnersproject.activities.signin;

import android.text.Editable;
import android.text.TextWatcher;

import ca.com.androidbinnersproject.interfaces.Callback;

/**
 * Created by leodeleon on 26/12/2016.
 */

public class LoginTextWatcher implements TextWatcher {
  Callback callback;

  public LoginTextWatcher(Callback callback) {
    this.callback = callback;
  }


  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {
    callback.callback();
  }
}
