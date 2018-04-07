package com.lounah.gallery.di;

import com.lounah.gallery.ui.feed.FeedFragment;
import com.lounah.gallery.ui.feed.FeedModule;
import com.lounah.gallery.ui.files.FilesFragment;
import com.lounah.gallery.ui.offline.OfflineFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module abstract class FragmentBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {FeedModule.class})
    abstract FeedFragment feedFragment();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract FilesFragment filesFragment();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract OfflineFragment offlineFragment();

}
