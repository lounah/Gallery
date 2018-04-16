package com.lounah.gallery.ui.trash;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.data.repository.PhotosRepository;
import com.lounah.gallery.util.AbsentLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TrashViewModel extends ViewModel {

    /*
        Я думаю, не совсем правильно для каждого экшна создавать обсервер
        TODO: конкатенировать в один обсервер
     */
    private final MutableLiveData<Boolean> clearActionResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deleteActionResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> moveToGalleryActionResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> refreshState = new MutableLiveData<>();

    private final PhotosRepository repository;

    private LiveData<List<Photo>> deletedPhotos;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    TrashViewModel(@NonNull final PhotosRepository repository) {
        this.repository = repository;
        deletedPhotos = Transformations.switchMap(refreshState, shouldRefresh -> {
            if (shouldRefresh) return this.repository.getDeletedPhotos(); else return AbsentLiveData.create();
        });

    }

    LiveData<List<Photo>> getDeletedPhotos() {
        if (deletedPhotos == null) deletedPhotos = new MutableLiveData<>();
        return deletedPhotos;
    }

    LiveData<Boolean> getClearActionResult() {
        return clearActionResult;
    }

    LiveData<Boolean> getDeleteActionResult() {
        return deleteActionResult;
    }

    LiveData<Boolean> getMoveToGalleryActionResult() {
        return moveToGalleryActionResult;
    }

    void deletePhotoPermanently(@NonNull final Photo photo) {
        disposables.add(repository.deletePhotoFromTrash(photo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> deleteActionResult.postValue(true),
                        e -> deleteActionResult.postValue(false)));
    }

    void movePhotoToGallery(@NonNull final Photo photo) {
        disposables.add(repository.movePhotoToGallery(photo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> moveToGalleryActionResult.postValue(true),
                        e -> moveToGalleryActionResult.postValue(false)));
    }

    void clearTrash() {
        disposables.add(repository.clearTrash()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> clearActionResult.postValue(true),
                        e -> clearActionResult.postValue(false)));
    }



    void refreshTrash() {
        refreshState.setValue(true);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposables != null)
            disposables.dispose();
    }


}
