
package ca.com.androidbinnersproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class StartApp extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		Intent intent = new Intent();

		if(isLogged())
		{
			intent.setClass(this, MainActivity.class);
		}
		else
		{
			intent.setClass(this, Login.class);
			startActivityForResult(intent, Login.FROM_LOGIN);
			return;
		}

		startActivity(intent);
    }

	public boolean isLogged()
	{
		SharedPreferences preferences = getSharedPreferences(Login.USER_AUTHENTICATED, 0);
		return preferences.getBoolean(Login.IS_AUTHENTICATED, false);
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == Login.FROM_LOGIN)
        {
            if(resultCode == RESULT_OK)
            {
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
            }
            else
			{
				Log.e("BinnersApp", "Failed to log in: " + resultCode);
			}
        }
        else
		{
			Log.e("BinnersApp", "Received unknown activity result: " + resultCode);
		}
    }
}
