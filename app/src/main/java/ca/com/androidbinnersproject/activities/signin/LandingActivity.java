package ca.com.androidbinnersproject.activities.signin;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.com.androidbinnersproject.R;

/**
 * Created by leodeleon on 20/12/2016.
 */

public class LandingActivity extends AppCompatActivity {

  @BindView(R.id.login_logo_layout) LinearLayout mLogoLayout;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);
    ButterKnife.bind(this);

    AnimationListener.Stop stopListener = new AnimationListener.Stop() {
      @Override
      public void onStop() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.nothing);
        transaction.add(R.id.login_container, new LoginFragment()).commit();
      }
    };

    mLogoLayout.setAlpha(0);
    ViewAnimator.animate(mLogoLayout)
      .alpha(0,1)
      .duration(500)
      .translationY(0, -300)
      .duration(1000)
      .onStop(stopListener).start();


  }

  public void showEmailLoginFragment() {

  }

  public void showForgotPasswordFragment() {

  }

  public void showCreateAccountFragment() {

  }




}
