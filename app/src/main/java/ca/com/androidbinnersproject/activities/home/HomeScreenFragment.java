
package ca.com.androidbinnersproject.activities.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.w3c.dom.Text;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.activities.pickup.PickupActivity;
import ca.com.androidbinnersproject.activities.pickup.PickupListFragment;
import ca.com.androidbinnersproject.adapters.ViewPagerAdapter;
import ca.com.androidbinnersproject.util.Util;

public class HomeScreenFragment extends Fragment {

	private Toolbar mToolbarBottom;

	public HomeScreenFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_screen, container, false);

	/*	newPickupButton = (Button) view.findViewById(R.id.home_newpickup_button);
		newPickupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PickupActivity.class);
                startActivity(intent);
            }
        });*/

		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		mToolbarBottom = (Toolbar) view.findViewById(R.id.home_screen_include_toolbar);

		setToolbatClickListener();

		super.onViewCreated(view, savedInstanceState);
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

			}
		});

		mToolbarBottom.findViewById(R.id.toolbar_bottom_btnPickup).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PickupActivity.class);
				startActivity(intent);
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
    public void onStart() {
        super.onStart();
    }
}
