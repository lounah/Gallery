package com.lounah.gallery.ui.navigation;


import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.lounah.gallery.R;
import com.lounah.gallery.ui.MainActivity;
import com.lounah.gallery.ui.feed.photodetails.PhotoDetailsFragment;

import javax.inject.Inject;

public class NavigationController {


    private static final int PHOTO_VIEW_CONTAINER_ID = R.id.photo_details_container;

    private final FragmentManager mFragmentManager;

    @Inject
    NavigationController(@NonNull final MainActivity activity) {
        mFragmentManager = activity.getSupportFragmentManager();
    }

    public void navigateToFeedPhotoDetails(final int initialPosition) {
        mFragmentManager
                .beginTransaction()
                .replace(PHOTO_VIEW_CONTAINER_ID, PhotoDetailsFragment.newInstance(initialPosition))
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .addToBackStack(PhotoDetailsFragment.class.getSimpleName())
                .commit();
    }

    public void popBackStack() {
        mFragmentManager
                .popBackStack();
    }

    public int getBackStackEntryCount() {
        return mFragmentManager
                .getBackStackEntryCount();
    }
}
