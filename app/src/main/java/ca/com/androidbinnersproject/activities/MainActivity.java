package ca.com.androidbinnersproject.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.ncapdevi.fragnav.FragNavController;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.history.HistoryPickupFragment;
import ca.com.androidbinnersproject.activities.home.HomeScreenFragment;
import ca.com.androidbinnersproject.activities.ongoing.OngoingPickupsFragment;
import ca.com.androidbinnersproject.activities.pickup.NewPickupFragment;
import ca.com.androidbinnersproject.util.Util;

public class MainActivity extends AppCompatActivity {
  @BindView(R.id.binners_toolbar)
  Toolbar mToolbar;
  @BindView(R.id.main_container_body)
  FrameLayout mContainerBody;
  @BindView(R.id.bottom_navigation_bar)
  AHBottomNavigation mBottomNavigationBar;
  @BindView(R.id.drawer_layout)
  DrawerLayout mDrawer;
  @BindView(R.id.drawer_navigation_view)
  NavigationView mNavigationView;

  //LeftNavigationDrawerMenu mFragmentDrawer;

  private HomeScreenFragment mHomeScreenMapFragment;
  private FragNavController mFragController;
  private Resources res;

  public static final int POSITION_HISTORY = 0;
  public static final int POSITION_ONGOING = 1;
  public static final int POSITION_PICKUP = 2;
  public static final int POSITION_DONATE = 3;
  private final int INDEX_HISTORY = FragNavController.TAB1;
  private final int INDEX_ONGOING = FragNavController.TAB2;
  private final int INDEX_PICKUP = FragNavController.TAB3;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    res = this.getResources();

    final String userLogged = Util.getUserLogged();
    //mFragmentDrawer.setUp(R.id.fragment_navigation_drawer, mDrawer, mToolbar, userLogged);

    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    //mFragmentDrawer.setDrawerListener(this);
    mHomeScreenMapFragment = new HomeScreenFragment();
    getSupportFragmentManager().beginTransaction()
        .add(R.id.main_container_body, mHomeScreenMapFragment)
        .commit();
    createBottomNavigationTabs();
  }

  private void createBottomNavigationTabs() {

    mBottomNavigationBar.addItem(new AHBottomNavigationItem(res.getString(R.string.history),R.drawable.ic_buttom_history, R.color.grey_600));
    mBottomNavigationBar.addItem(new AHBottomNavigationItem(res.getString(R.string.ongoing),R.drawable.ic_buttom_ongoing, R.color.grey_600));
    mBottomNavigationBar.addItem(new AHBottomNavigationItem(res.getString(R.string.pickup),R.drawable.ic_buttom_pickup, R.color.grey_600));
    mBottomNavigationBar.addItem(new AHBottomNavigationItem(res.getString(R.string.donate),R.drawable.ic_buttom_donate, R.color.grey_600));


    ArrayList<Fragment> fragments = new ArrayList<>();
    fragments.add(HistoryPickupFragment.newInstance());
    fragments.add(OngoingPickupsFragment.newInstance(this));
    fragments.add(NewPickupFragment.newInstance(MainActivity.this, mHomeScreenMapFragment.getLatLng().latitude, mHomeScreenMapFragment.getLatLng().longitude));

    mFragController = new FragNavController(getSupportFragmentManager(), R.id.main_container_body, fragments);

    mBottomNavigationBar.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
      @Override
      public boolean onTabSelected(int position, boolean wasSelected) {
        switch (position) {
          case POSITION_HISTORY:
            mFragController.switchTab(INDEX_HISTORY);
            break;
          case POSITION_ONGOING:
            mFragController.switchTab(INDEX_ONGOING);
            break;
          case POSITION_PICKUP:
            mFragController.switchTab(INDEX_PICKUP);
            break;
          case POSITION_DONATE:

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(MainActivity.this, Uri.parse(res.getString(R.string.url_donate)));

            break;
        }

        return true;
      }
    });

    mBottomNavigationBar.setCurrentItem(POSITION_PICKUP);

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawer.openDrawer(GravityCompat.START);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }


}
