package com.lounah.gallery.ui.feed;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.data.entity.Resource;
import com.lounah.gallery.data.repository.PhotosRepository;
import com.lounah.gallery.util.AbsentLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FeedViewModel extends ViewModel {

    /*
        observer резульатата удаления фотографии;
        сделан для того, чтобы фрагмент не обращался к репозиторию за результатом
        напрямую
     */
    private final MutableLiveData<Boolean> successDeletionResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> refreshState = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final PhotosRepository repository;
    private LiveData<Resource<List<Photo>>> feed;

    @Inject
    FeedViewModel(@NonNull final PhotosRepository repository) {
        this.repository = repository;
        feed = Transformations.switchMap(refreshState, shouldRefresh -> {
           if (shouldRefresh) return this.repository.getFeed(true);
           else return this.repository.getFeed(false);
        });
    }

    public LiveData<Resource<List<Photo>>> getUserFeed() {
        if (feed == null) feed = new MutableLiveData<>();
        return feed;
    }

    LiveData<Boolean> getSuccessDeletionResult() {
        return successDeletionResult;
    }

    void deletePhoto(@NonNull final Photo photo) {
        disposables.add(repository.deletePhoto(photo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(() -> successDeletionResult.postValue(true),
                    e -> successDeletionResult.postValue(false)));
    }

    /*
         forceRefresh(true/false) -- обновить, обратившись к серверу/обновить,
         обратившись к локальной БД
     */
    void refreshFeed(final boolean forceRefresh) {
        refreshState.setValue(forceRefresh);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposables != null)
            disposables.dispose();
    }

}
