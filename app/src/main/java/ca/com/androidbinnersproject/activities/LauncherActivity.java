
package ca.com.androidbinnersproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.FacebookSdk;
import ca.com.androidbinnersproject.activities.signin.LandingActivity;
import ca.com.androidbinnersproject.models.Profile;
import ca.com.androidbinnersproject.util.BinnersSettings;

public class LauncherActivity extends Activity {
  private final String TAG = "LauncherActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

		BinnersSettings.initialize(getApplicationContext());
    FacebookSdk.sdkInitialize(getApplicationContext());

    Intent intent = new Intent();

    if (Profile.getInstance().hasUser()) {
      intent.setClass(this, MainActivity.class);
    } else {
      intent.setClass(this, LandingActivity.class);
    }
    startActivity(intent);
    finish();
  }

  @Override
  protected void onRestart() {
    super.onRestart();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

}
