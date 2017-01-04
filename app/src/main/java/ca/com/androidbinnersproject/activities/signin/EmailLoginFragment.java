package ca.com.androidbinnersproject.activities.signin;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.auth.Authentication;
import ca.com.androidbinnersproject.interfaces.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailLoginFragment extends Fragment {


  @BindView(R.id.login_input_email)
  TextInputLayout mEmailLayout;
  @BindView(R.id.login_input_password)
  TextInputLayout mPasswordLayout;
  @BindView(R.id.login_email)
  TextInputEditText mEmailEditText;
  @BindView(R.id.login_password)
  TextInputEditText mPasswordEditText;

  private Authentication authentication;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.email_login_fragment, container, false);
    ButterKnife.bind(this, view);

    mEmailEditText.addTextChangedListener(new LoginTextWatcher(new Callback() {
      @Override
      public void callback() {
        ((LandingActivity) getActivity()).validateEmail(mEmailEditText, mEmailLayout);
      }
    }));

    return view;
  }

  @OnClick({R.id.login_forgot})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.login_forgot:
        ((LandingActivity) getActivity()).showForgotPasswordFragment();
        break;
      case R.id.login_button:

        break;
    }
  }


}
