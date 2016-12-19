
package ca.com.androidbinnersproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import ca.com.androidbinnersproject.activities.login.LoginActivity;
import ca.com.androidbinnersproject.util.BinnersSettings;

public class StartAppActivity extends Activity {
	private final String TAG = "StartAppActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		BinnersSettings.initialize(getApplicationContext());

		Intent intent = new Intent();

		if(isLogged()) {
			intent.setClass(this, MainActivity.class);
			finish();
		}  else {
			intent.setClass(this, LoginActivity.class);
			startActivityForResult(intent, LoginActivity.FROM_LOGIN);
			return;
		}


		startActivity(intent);
    }

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public boolean isLogged() {
		return BinnersSettings.getToken().length() > 0;
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LoginActivity.FROM_LOGIN) {
            if(resultCode == RESULT_OK) {
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
            }
            else {
				Log.e(TAG, "Failed to log in: " + resultCode);
			}
        }
        else {
			Log.e(TAG, "Received unknown activity result: " + resultCode);
		}
    }
}
