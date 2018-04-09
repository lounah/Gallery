package com.lounah.gallery.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lounah.gallery.R;
import com.lounah.gallery.ui.feed.FeedFragment;
import com.lounah.gallery.ui.offline.OfflineFragment;
import com.lounah.gallery.ui.trash.TrashFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;

public class MainActivity extends DaggerAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navView;

    @BindView(R.id.tabs_main)
    SmartTabLayout tabLayout;

    @BindView(R.id.viewpager_main)
    ViewPager viewPager;

    private static final int FEED_POSITION = 0;
    private static final int OFFLINE_POSITION = 1;
    private static final int TRASH_POSITION = 2;

    private ActionBarDrawerToggle toggle;
    private FragmentPagerItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {}
        // mNavController.navigateToFeed();
        initUI();

        Timber.i("ON CREATE");
    }

    private void initUI() {
        ButterKnife.bind(this);

        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add(R.string.feed, FeedFragment.class)
                        .add(R.string.offline, OfflineFragment.class)
                        .add(R.string.trash, TrashFragment.class)
                        .create());

        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);

        tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case FEED_POSITION:
                        navView.setCheckedItem(R.id.nav_feed);
                        break;
                    case OFFLINE_POSITION:
                        navView.setCheckedItem(R.id.nav_offline);
                        break;
                    case TRASH_POSITION:
                        navView.setCheckedItem(R.id.nav_trash);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);

    }

    public void onUpdateToolbar(@NonNull final Toolbar toolbar) {

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        if ((mNavController.getBackStackCount() > 0) &&
//                (getSupportActionBar() != null) &&
//                (mNavController != null)) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//            toolbar.setNavigationOnClickListener(v -> onBackPressed());
//        } else {
//            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
//        } else if (mNavController.getBackStackCount() > 0)
//            mNavController.navigateBack(); else super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_feed:
                viewPager.setCurrentItem(FEED_POSITION);
                break;

            case R.id.nav_offline:
                viewPager.setCurrentItem(OFFLINE_POSITION);
                fabAdd.hide();
                break;

            case R.id.nav_trash:
                viewPager.setCurrentItem(TRASH_POSITION);
                break;
//
//            case R.id.nav_trash:
//                mNavController.navigateToTrash();
//                break;
//
//            case R.id.nav_clear_space:
//                mNavController.navigateToClearSpace();
//                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
