package com.lounah.gallery.di;

import com.lounah.gallery.ui.feed.FeedFragment;
import com.lounah.gallery.ui.offline.OfflineFragment;
import com.lounah.gallery.ui.trash.TrashFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract FeedFragment feedFragment();

    @ContributesAndroidInjector
    abstract OfflineFragment offlineFragment();

    @ContributesAndroidInjector
    abstract TrashFragment trashFragment();

}
