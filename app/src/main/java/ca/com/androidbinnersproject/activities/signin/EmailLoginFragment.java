package ca.com.androidbinnersproject.activities.signin;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import ca.com.androidbinnersproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailLoginFragment extends Fragment {


  @BindView(R.id.login_input_password)
  TextInputLayout mPasswordLayout;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.email_login_fragment, container, false);
  }
}
