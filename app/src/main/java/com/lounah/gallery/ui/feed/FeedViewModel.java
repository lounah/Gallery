package com.lounah.gallery.ui.feed;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.data.entity.Resource;
import com.lounah.gallery.data.repository.FeedRepository;
import com.lounah.gallery.util.AbsentLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class FeedViewModel extends ViewModel {

    private final MutableLiveData<Photo> selectedPhoto = new MutableLiveData<>();
    private final MutableLiveData<Boolean> refreshState = new MutableLiveData<>();
    private final FeedRepository repository;
    private LiveData<Resource<List<Photo>>> feed;

    @Inject
    FeedViewModel(@NonNull final FeedRepository repository) {
        this.repository = repository;
        feed = Transformations.switchMap(refreshState, shouldRefresh -> {
           if (shouldRefresh) return repository.get(); else return AbsentLiveData.create();
        });
    }

    public void selectPhoto(@NonNull final Photo photo) {
        selectedPhoto.setValue(photo);
    }

    public LiveData<Photo> getSelectedPhoto() {
        return selectedPhoto;
    }

    LiveData<Resource<List<Photo>>> getUserFeed() {
        if (feed == null) feed = new MutableLiveData<>();
        return feed;
    }

    void refreshFeed() {
        refreshState.setValue(true);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
