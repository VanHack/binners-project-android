package ca.com.androidbinnersproject.activities;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.ncapdevi.fragnav.FragNavController;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.history.HistoryPickupFragment;
import ca.com.androidbinnersproject.activities.home.MapPickupFragment;
import ca.com.androidbinnersproject.activities.ongoing.OngoingPickupsFragment;

public class MainActivity extends AppCompatActivity {
  @BindView(R.id.binners_toolbar)
  Toolbar mToolbar;
  @BindView(R.id.main_container_body)
  FrameLayout mContainerBody;
  @BindView(R.id.bottom_navigation_bar)
  AHBottomNavigation mBottomNavigationBar;
  @BindView(R.id.drawer_layout)
  DrawerLayout mDrawerLayout;
  @BindView(R.id.drawer_navigation_view)
  NavigationView mNavigationView;

  private ActionBarDrawerToggle mActionBarDrawerToggle;
  private FragNavController mFragController;
  private Resources res;
  private AHBottomNavigationAdapter mBottomNavigationAdapter;

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
    setSupportActionBar(mToolbar);
    createBottomNavigationTabs();
    setupDrawerContent();
  }


  private void createBottomNavigationTabs() {
    mBottomNavigationBar.setForceTitlesDisplay(true);
    mBottomNavigationBar.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.green_400) );
    mBottomNavigationBar.setAccentColor(ContextCompat.getColor(this, R.color.white));
    mBottomNavigationBar.setInactiveColor(ContextCompat.getColor(this, R.color.green_700));
    mBottomNavigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_menu);
    mBottomNavigationAdapter.setupWithBottomNavigation(mBottomNavigationBar);

    final ArrayList<Fragment> fragments = new ArrayList<>();
    fragments.add(HistoryPickupFragment.newInstance());
    fragments.add(OngoingPickupsFragment.newInstance(this));
    fragments.add(MapPickupFragment.newInstance());

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
            mBottomNavigationBar.setSelected(false);

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
        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setupDrawerContent() {
    mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
    mActionBarDrawerToggle.syncState();
    mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

    mNavigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
            selectDrawerItem(menuItem);
            return true;
          }
        });
  }

  public void selectDrawerItem(MenuItem menuItem) {
    // Create a new fragment and specify the fragment to show based on nav item clicked
    Fragment fragment = null;
    Class fragmentClass = null;
    switch(menuItem.getItemId()) {
      case R.id.drawer_menu_my_profile:
        fragmentClass = MapPickupFragment.class;
        break;
      case R.id.drawer_menu_donate:
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.green_400));
        builder.addDefaultShareMenuItem();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(MainActivity.this, Uri.parse(res.getString(R.string.url_donate)));
        break;
      case R.id.drawer_menu_binners:
        fragmentClass = MapPickupFragment.class;
        break;
      case R.id.drawer_menu_account:
        fragmentClass = MapPickupFragment.class;
        break;
      case R.id.drawer_menu_help:
        fragmentClass = MapPickupFragment.class;
        break;
      default:
        fragmentClass = MapPickupFragment.class;
    }

    if (fragmentClass != null) {
      try {
        fragment = (Fragment) fragmentClass.newInstance();
      } catch (Exception e) {
        e.printStackTrace();
      }

      FragmentManager fragmentManager = getSupportFragmentManager();
      fragmentManager.beginTransaction().replace(R.id.main_container_body, fragment).commit();
    }


    // Highlight the selected item has been done by NavigationView
    menuItem.setChecked(true);
    // Set action bar title
    setTitle(menuItem.getTitle());
    // Close the navigation drawer
    mDrawerLayout.closeDrawers();
  }


}
