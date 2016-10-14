package ca.com.androidbinnersproject.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.adapters.LeftDrawerAdapter;
import ca.com.androidbinnersproject.models.NavDrawerItem;
import ca.com.androidbinnersproject.util.Util;

/**
 * Created by jonathan_campos on 12/03/2016.
 */
public class LeftNavigationDrawerMenu extends Fragment {
    private RecyclerView mRecyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private LeftDrawerAdapter mAdapter;
    private View mContainerView;
    private static String[] mTitles = null;
    private FragmentDrawerListener mDrawerListener;

    private ImageView imgProfile;
    private TextView txtProfile;

    public LeftNavigationDrawerMenu(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.left_navigation_drawer_menu, container, false);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.left_navigation_drawer_menu_drawerList);

        mAdapter = new LeftDrawerAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDrawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(mContainerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar, final String userName) {
        mContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                toolbar.setAlpha(1 - slideOffset / 2);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        imgProfile = (ImageView) getActivity().findViewById(R.id.left_navigation_drawer_menu_profile_picture);
        txtProfile  = (TextView) getActivity().findViewById(R.id.left_navigation_drawer_menu_profile_name);

        loadProfilePicture();
        loadProfileName();
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        mDrawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();

        for (String title: mTitles) {
            NavDrawerItem item = new NavDrawerItem();
            item.setTitle(title);
            data.add(item);
        }

        return data;
    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }


    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    private void loadProfileName() {
        txtProfile.setText(Util.getUserLogged());
    }

    private void loadProfilePicture() {
        Glide.with(this).load(R.drawable.nopicture).asBitmap().into(new BitmapImageViewTarget(imgProfile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgProfile.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}
