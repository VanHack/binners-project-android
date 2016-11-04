package ca.com.androidbinnersproject.activities;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

    mBottomNavigationBar.addItem(new AHBottomNavigationItem(res.getString(R.string.history),R.drawable.ic_buttom_history, R.color.grey_600));
    mBottomNavigationBar.addItem(new AHBottomNavigationItem(res.getString(R.string.ongoing),R.drawable.ic_buttom_ongoing, R.color.grey_600));
    mBottomNavigationBar.addItem(new AHBottomNavigationItem(res.getString(R.string.pickup),R.drawable.ic_buttom_pickup, R.color.grey_600));
    mBottomNavigationBar.addItem(new AHBottomNavigationItem(res.getString(R.string.donate),R.drawable.ic_buttom_donate, R.color.grey_600));


    ArrayList<Fragment> fragments = new ArrayList<>();
    fragments.add(HistoryPickupFragment.newInstance());
    fragments.add(OngoingPickupsFragment.newInstance(this));
    fragments.add(HomeScreenFragment.newInstance());

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
    Class fragmentClass;
    switch(menuItem.getItemId()) {
      case R.id.drawer_menu_my_profile:
        fragmentClass = HomeScreenFragment.class;
        break;
      case R.id.drawer_menu_donate:
        fragmentClass = HomeScreenFragment.class;
        break;
      case R.id.drawer_menu_binners:
        fragmentClass = HomeScreenFragment.class;
        break;
      case R.id.drawer_menu_account:
        fragmentClass = HomeScreenFragment.class;
        break;
      case R.id.drawer_menu_help:
        fragmentClass = HomeScreenFragment.class;
        break;
      default:
        fragmentClass = HomeScreenFragment.class;
    }

    try {
      fragment = (Fragment) fragmentClass.newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }

    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.main_container_body, fragment).commit();

    // Highlight the selected item has been done by NavigationView
    menuItem.setChecked(true);
    // Set action bar title
    setTitle(menuItem.getTitle());
    // Close the navigation drawer
    mDrawerLayout.closeDrawers();
  }


}
