package com.lounah.gallery.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.lounah.gallery.ui.GalleryViewModelFactory;
import com.lounah.gallery.ui.feed.FeedViewModel;
import com.lounah.gallery.ui.offline.OfflineFragmentViewModel;
import com.lounah.gallery.ui.trash.TrashViewModel;

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
    @IntoMap
    @ViewModelKey(OfflineFragmentViewModel.class)
    abstract ViewModel bindOfflineFragmentViewModel(OfflineFragmentViewModel offlineFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TrashViewModel.class)
    abstract ViewModel bindTrashViewModel(TrashViewModel trashViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(GalleryViewModelFactory factory);

}
