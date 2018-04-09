package com.lounah.gallery.data.repository;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lounah.gallery.data.datasource.local.feed.FeedDao;
import com.lounah.gallery.data.datasource.remote.GalleryService;
import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.data.entity.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class FeedRepository implements BaseRepository<Photo> {

    private final GalleryService api;
    private final FeedDao dao;

    @Inject
    public FeedRepository(@NonNull final GalleryService api, @NonNull final FeedDao dao) {
        this.api = api;
        this.dao = dao;
    }


    @Override
    public LiveData<Resource<List<Photo>>> get() {
        return new NetworkBoundResource<List<Photo>>() {

            @Override
            protected void saveCallResult(@NonNull List<Photo> photos) {
                dao.insertAll(photos);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Photo> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Photo>> loadFromDb() {
                return dao.getPhotos();
            }

            @NonNull
            @Override
            protected Single<List<Photo>> createCall() {
                return api.getFeed();
            }

            @Override
            protected void onFetchFailed() {
                Timber.i("FETCH FAILED");
            }

        }.getAsLiveData();
    }

    @Override
    public Completable add(Photo data) {
        return null;
    }

}
