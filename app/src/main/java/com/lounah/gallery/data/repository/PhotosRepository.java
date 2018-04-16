package com.lounah.gallery.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.lounah.gallery.data.datasource.local.PhotosDao;
import com.lounah.gallery.data.datasource.remote.GalleryService;
import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.data.entity.Resource;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import timber.log.Timber;

public class PhotosRepository {

    private static final int DATA_SOURCE_QUANTITY_LIMIT = 100;
    private static final String QUERY_MEDIA_TYPE = "image";
    private static final String QUERY_UPLOAD_FIELD = "href";

    private final PhotosDao photosDao;
    private final GalleryService api;

    @Inject
    public PhotosRepository(
            @NonNull final GalleryService api,
            @NonNull final PhotosDao photosDao) {
        this.api = api;
        this.photosDao = photosDao;
    }

    public LiveData<Resource<List<Photo>>> getFeed(final boolean forceRefresh) {
            return new NetworkBoundResource<List<Photo>>(forceRefresh) {

                @Override
                protected void saveCallResult(@NonNull List<Photo> photos) {
                    photosDao.insertAll(photos);
                }

                @NonNull
                @Override
                protected LiveData<List<Photo>> loadFromDb() {
                    return photosDao.getFeed();
                }

                @Override
                protected int getNumRows() {
                    return getNumRowsFromFeed();
                }

                @NonNull
                @Override
                protected Single<List<Photo>> createCall() {
                    return api.getFeed(DATA_SOURCE_QUANTITY_LIMIT, QUERY_MEDIA_TYPE);
                }

                @Override
                protected void onFetchFailed() {
                    Timber.i("FETCH FAILED");
                }

            }.getAsLiveData();
    }

    public LiveData<List<Photo>> getDeletedPhotos() {
        return photosDao.getDeletedPhotos();
    }



    public Completable deletePhoto(@NonNull Photo photo) {
        return api.deletePhoto(photo.getPath(), false).doOnComplete(() -> {
           photo.setInTrash(true);
           photosDao.update(photo);
        });
    }

    public int getNumRowsFromFeed() {
       final int[] rows = new int[1];
       Completable.fromAction(() -> rows[0] = photosDao.getPersistedPhotosSize())
               .subscribeOn(Schedulers.io())
               .blockingAwait();
       return rows[0];
    }

    public LiveData<List<String>> getSavedPhotosFilePaths(@NonNull final File savedPhotosDirectory) {
        final MutableLiveData<List<String>> result = new MutableLiveData<>();

        Observable.fromArray(savedPhotosDirectory.listFiles())
                .map(File::getAbsolutePath)
                .toList()
                .subscribeOn(Schedulers.io())
                .subscribe(result::postValue);

        return result;
    }

    public Completable uploadPhoto(@NonNull final String fileName,
                                   @NonNull final MultipartBody.Part image) {
        return api.getUploadUrl(fileName, QUERY_UPLOAD_FIELD)
                .doOnSuccess(uploadUrl -> api.uploadPhoto(uploadUrl, image))
                .toCompletable();
    }

    public Completable deletePhotoFromTrash(@NonNull final Photo photo) {
        return Completable.fromAction(() -> photosDao.delete(photo));
    }

    public Completable movePhotoToGallery(@NonNull Photo photo) {
        return api.movePhotoToGallery(photo.getName())
                .doOnComplete(() -> {
                photo.setInTrash(false);
                photosDao.update(photo);
        });
    }

    public Completable clearTrash() {
        return api.clearTrash().doOnComplete(photosDao::eraseAll);
    }

}
