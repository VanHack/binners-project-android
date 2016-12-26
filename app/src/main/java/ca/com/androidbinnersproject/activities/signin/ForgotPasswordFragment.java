package ca.com.androidbinnersproject.activities.signin;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ca.com.androidbinnersproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {

  @BindView(R.id.forgot_email_layout)
  TextInputLayout mEmailInputLayout;
  @BindView(R.id.forgot_message)
  TextView mMessageText;
  @BindView(R.id.forgot_reset_button)
  AppCompatButton mResetButton;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.forgot_password_fragment, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @OnClick({R.id.forgot_reset_button})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.forgot_reset_button:
        mResetButton.setText(R.string.back);
        mMessageText.setText(R.string.forgot_password_success);
        mEmailInputLayout.setVisibility(View.GONE);
        break;
    }
  }

}
