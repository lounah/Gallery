package com.lounah.gallery.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.lounah.gallery.ui.GalleryViewModelFactory;
import com.lounah.gallery.ui.feed.FeedViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel.class)
    abstract ViewModel bindFeedViewModel(FeedViewModel feedViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(GalleryViewModelFactory factory);

}
