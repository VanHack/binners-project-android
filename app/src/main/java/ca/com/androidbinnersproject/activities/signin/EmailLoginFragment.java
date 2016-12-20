package ca.com.androidbinnersproject.activities.signin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.com.androidbinnersproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailLoginFragment extends Fragment {


  public EmailLoginFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.email_login_fragment, container, false);
  }

}
