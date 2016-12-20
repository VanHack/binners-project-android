package ca.com.androidbinnersproject.activities.signin;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import ca.com.androidbinnersproject.R;

/**
 * Created by leodeleon on 20/12/2016.
 */

public class LandingActivity extends AppCompatActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);
    ButterKnife.bind(this);
  }
}
