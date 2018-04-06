package com.lounah.gallery.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lounah.gallery.R;
import com.lounah.gallery.ui.allphotos.AllPhotosFragment;
import com.lounah.gallery.ui.feed.FeedFragment;
import com.lounah.gallery.ui.files.FilesFragment;
import com.lounah.gallery.ui.offline.OfflineFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

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
    private static final int FILES_POSITION = 1;
    private static final int ALL_PHOTOS_POSITION = 2;
    private static final int OFFLINE_POSITION = 3;

    private ActionBarDrawerToggle toggle;
    private FragmentPagerItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {}
        // mNavController.navigateToFeed();
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);

        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add(R.string.feed, FeedFragment.class)
                        .add(R.string.files, FilesFragment.class)
                        .add(R.string.all_photos, AllPhotosFragment.class)
                        .add(R.string.offline, OfflineFragment.class)
                        .create());

        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_feed:
              //  viewPager.setCurrentItem(id);
                viewPager.setCurrentItem(FEED_POSITION);
                break;

            case R.id.nav_files:
                viewPager.setCurrentItem(FILES_POSITION);
                break;

            case R.id.nav_all_photos:
                viewPager.setCurrentItem(ALL_PHOTOS_POSITION);
                break;

            case R.id.nav_offline:
                viewPager.setCurrentItem(OFFLINE_POSITION);
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
