package ca.com.androidbinnersproject.activities.signin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.widget.LoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ca.com.androidbinnersproject.R;

public class LoginFragment extends Fragment {
  private static final String TAG = "LoginFragment";

  @BindView(R.id.login_facebook_widget) LoginButton mFbButton;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.login_fragment, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @OnClick({R.id.login_facebook_button, R.id.login_button, R.id.login_signup_button})
  public void onClick(View view){
    switch (view.getId()) {
      case R.id.login_facebook_button:
        mFbButton.performClick();
        break;
      case R.id.login_button:
        ((LandingActivity) getActivity()).showEmailLoginFragment();
        break;
      case R.id.login_signup_button:
        ((LandingActivity) getActivity()).showCreateAccountFragment();
        break;
    }
  }

}
