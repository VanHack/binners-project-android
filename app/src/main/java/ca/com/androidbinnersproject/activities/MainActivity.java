
package ca.com.androidbinnersproject.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.home.HomeScreenFragment;
import ca.com.androidbinnersproject.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LeftNavigationDrawerMenu.FragmentDrawerListener {

    private ImageButton btnSetDate;

    private Toolbar mToolbar;
    private LeftNavigationDrawerMenu mFragmentDrawer;
    private FrameLayout containerBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final String userLogged = Util.getUserLogged(this);

        mFragmentDrawer = (LeftNavigationDrawerMenu) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mFragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar, userLogged);
        mFragmentDrawer.setDrawerListener(this);

        containerBody = (FrameLayout) findViewById(R.id.main_container_body);

        HomeScreenFragment homeScreenFragment = new HomeScreenFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.main_container_body, homeScreenFragment)
                .commit();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }
}
