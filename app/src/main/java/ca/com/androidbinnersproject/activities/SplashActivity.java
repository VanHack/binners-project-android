package ca.com.androidbinnersproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by jonathan_campos on 08/02/2016.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, LauncherActivity.class);
        startActivity(intent);
        finish();
    }
}
