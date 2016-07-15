
package ca.com.androidbinnersproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.home.HomeScreenFragment;
import ca.com.androidbinnersproject.activities.ongoing.OngoingPickupsFragment;
import ca.com.androidbinnersproject.activities.pickup.PickupActivity;
import ca.com.androidbinnersproject.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LeftNavigationDrawerMenu.FragmentDrawerListener {
    private FragmentManager mFragmentManager;
    private Toolbar mToolbar;
    private LeftNavigationDrawerMenu mFragmentDrawer;
    private Toolbar mToolbarBottom;

    private HomeScreenFragment mHomeScreenMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final String userLogged = Util.getUserLogged(this);

        mFragmentDrawer = (LeftNavigationDrawerMenu) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mFragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar, userLogged);
        mFragmentDrawer.setDrawerListener(this);

        mToolbarBottom = (Toolbar) findViewById(R.id.home_screen_include_toolbar);

        mHomeScreenMapFragment = new HomeScreenFragment();

        setToolbatClickListener();

        showHomeScreen();
    }

    private void showHomeScreen() {
        mFragmentManager.beginTransaction()
                .add(R.id.main_container_body, mHomeScreenMapFragment)
                .addToBackStack("home")
                .commit();
    }

    private void setToolbatClickListener() {
        mToolbarBottom.findViewById(R.id.toolbar_bottom_btnHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mToolbarBottom.findViewById(R.id.toolbar_bottom_btnOngoing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_container_body, OngoingPickupsFragment.newInstance())
                        .addToBackStack("ongoing")
                        .commit();
            }
        });

        mToolbarBottom.findViewById(R.id.toolbar_bottom_btnPickup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeScreenMapFragment != null) {

                    Intent intent = new Intent(MainActivity.this, PickupActivity.class);
                    intent.putExtra("LAT", mHomeScreenMapFragment.getmLatLng().latitude);
                    intent.putExtra("LON", mHomeScreenMapFragment.getmLatLng().longitude);

                    startActivity(intent);
                }
            }
        });

        mToolbarBottom.findViewById(R.id.toolbar_bottom_btnNotifications).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mToolbarBottom.findViewById(R.id.toolbar_bottom_btnDonate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
        } else
            super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }
}
