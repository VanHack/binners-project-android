package ca.com.androidbinnersproject.activities.signin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ca.com.androidbinnersproject.R;

public class LoginFragment extends Fragment {


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.login_fragment, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

}
