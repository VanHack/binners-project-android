
package ca.com.androidbinnersproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.home.HomeScreenFragment;
import ca.com.androidbinnersproject.activities.ongoing.OngoingPickupsFragment;
import ca.com.androidbinnersproject.activities.pickup.PickupActivity;
import ca.com.androidbinnersproject.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LeftNavigationDrawerMenu.FragmentDrawerListener {
    private FragmentManager mFragmentManager;
    private Toolbar mToolbar;
    private LeftNavigationDrawerMenu mFragmentDrawer;
    private TabLayout mTabLayout;
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

        mTabLayout = (TabLayout) findViewById(R.id.home_screen_include_tabLayoutBottom);

        mHomeScreenMapFragment = new HomeScreenFragment();


        selectDefaultTabIndex();
        setTabLayoutClickListener();

        showHomeScreen();
    }

    private void selectDefaultTabIndex() {
        //Select Pickup Tab by default
        TabLayout.Tab tabAt = mTabLayout.getTabAt(2);
        tabAt.select();
    }

    private void showHomeScreen() {
        mFragmentManager.beginTransaction()
                .add(R.id.main_container_body, mHomeScreenMapFragment)
                .addToBackStack("home")
                .commit();
    }

    private void setTabLayoutClickListener() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                 showSelectedUI(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void showSelectedUI(final int position) {
        switch (position) {
            case 0:
                break;
            case 1:
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_container_body, OngoingPickupsFragment.newInstance(MainActivity.this))
                        .addToBackStack("ongoing")
                        .commit();
                break;
            case 2:
                if(mHomeScreenMapFragment != null) {

                    Intent intent = new Intent(MainActivity.this, PickupActivity.class);
                    intent.putExtra("LAT", mHomeScreenMapFragment.getmLatLng().latitude);
                    intent.putExtra("LON", mHomeScreenMapFragment.getmLatLng().longitude);

                    startActivity(intent);
                }
                break;
            case 3:
                break;
            case 4:
                break;
        }
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
