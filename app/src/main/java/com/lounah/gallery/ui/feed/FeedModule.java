package com.lounah.gallery.ui.feed;

import com.lounah.gallery.di.FragmentScoped;

import dagger.Module;
import dagger.Provides;

@Module
public class FeedModule {

    @Provides
    @FragmentScoped
    PhotoOnClickListener provideItemClickListener(FeedFragment fragment) {
        return fragment::onItemClicked;
    }

    @Provides
    @FragmentScoped
    FeedAdapter provideAdapter(final PhotoOnClickListener clickListener) {
        return new FeedAdapter(clickListener);
    }

}
