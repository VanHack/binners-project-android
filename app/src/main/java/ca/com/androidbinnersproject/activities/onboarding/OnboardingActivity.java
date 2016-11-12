package ca.com.androidbinnersproject.activities.onboarding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;

import java.util.ArrayList;
import java.util.List;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.adapters.StartAppPageAdapter;
import ca.com.androidbinnersproject.listeners.OnSkipListener;

/**
 * Created by jonathan_campos on 08/02/2016.
 */
public class OnboardingActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(Onboarding1Fragment.newInstance());
        addSlide(Onboarding2Fragment.newInstance());
        addSlide(Onboarding3Fragment.newInstance());

        showSkipButton(false);

    }


}
