package ca.com.androidbinnersproject.activities.about;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.adapters.StartAppPageAdapter;
import ca.com.androidbinnersproject.listeners.OnSkipListener;

/**
 * Created by jonathan_campos on 08/02/2016.
 */
public class AboutAppActivity extends FragmentActivity implements OnSkipListener {
    private StartAppPageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_app_layout);

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(new Onboarding1Fragment());
        fragmentList.add(new Onboarding2Fragment());
        fragmentList.add(new AboutPageThree());

        mPageAdapter = new StartAppPageAdapter(getSupportFragmentManager(), fragmentList);

        ViewPager viewPager = (ViewPager) findViewById(R.id.my_view_pager);

        viewPager.setAdapter(mPageAdapter);
    }

    @Override
    public void onClick() {
        finish();
    }

    /**
     * Force the user click on the Skip button.
     */
    @Override
    public void onBackPressed() {
        return;
    }
}
