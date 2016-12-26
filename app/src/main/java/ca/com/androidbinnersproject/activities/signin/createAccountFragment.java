package ca.com.androidbinnersproject.activities.signin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ca.com.androidbinnersproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.create_account_fragment, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @OnClick({R.id.signup_button})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.signup_button:
        ((LandingActivity) getActivity()).showForgotPasswordFragment();
        break;
    }
  }

}
