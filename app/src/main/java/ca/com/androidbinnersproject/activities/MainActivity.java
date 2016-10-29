
package ca.com.androidbinnersproject.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.history.HistoryPickupFragment;
import ca.com.androidbinnersproject.activities.home.HomeScreenFragment;
import ca.com.androidbinnersproject.activities.ongoing.OngoingPickupsFragment;
import ca.com.androidbinnersproject.activities.pickup.NewPickupFragment;
import ca.com.androidbinnersproject.util.Util;

public class MainActivity extends AppCompatActivity implements LeftNavigationDrawerMenu.FragmentDrawerListener,
        BottomNavigationBar.OnTabSelectedListener {
    private Toolbar mToolbar;
    private LeftNavigationDrawerMenu mFragmentDrawer;
    private BottomNavigationBar mBottomNavigationBar;

    private HomeScreenFragment mHomeScreenMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFragmentDrawer = (LeftNavigationDrawerMenu) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.home_screen_include_tabLayoutBottom);

        final String userLogged = Util.getUserLogged();
        mFragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar, userLogged);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mFragmentDrawer.setDrawerListener(this);
        mHomeScreenMapFragment = new HomeScreenFragment();

        createBottomNavigationTabs();
        selectDefaultTabIndex();
        showHomeScreen();
    }

    private void createBottomNavigationTabs() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setTabSelectedListener(this);

        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_buttom_history, getString(R.string.home_screen_toolbar_history_title)))
                .addItem(new BottomNavigationItem(R.drawable.ic_buttom_ongoing, getString(R.string.home_screen_toolbar_ongoing_title)))
                .addItem(new BottomNavigationItem(R.drawable.ic_buttom_pickup, getString(R.string.home_screen_toolbar_pickup_title)))
                .addItem(new BottomNavigationItem(R.drawable.ic_buttom_donate, getString(R.string.home_screen_toolbar_donate_title)))
                .setActiveColor(R.color.backgroundApp)
                .initialise();
    }

    private void selectDefaultTabIndex() {
        //Select Pickup Tab by default
        mBottomNavigationBar.setFirstSelectedPosition(2);
    }

    private void showHomeScreen() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container_body, mHomeScreenMapFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else
            super.onBackPressed();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0: //History
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container_body, HistoryPickupFragment.newInstance())
                        .addToBackStack("history")
                        .commit();
                break;
            case 1: //Ongoing
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container_body, OngoingPickupsFragment.newInstance(MainActivity.this))
                        .addToBackStack("ongoing")
                        .commit();
                break;
            case 2: //New Pickup
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container_body, NewPickupFragment.newInstance(MainActivity.this,
                                mHomeScreenMapFragment.getLatLng().latitude,
                                mHomeScreenMapFragment.getLatLng().longitude))
                        .addToBackStack("pickup")
                        .commit();

                break;
            case 3: //Donate
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gifttool.com/donations/Donate?ID=1453&AID=503&PID=4805"));
                startActivity(browserIntent);
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
