package com.lounah.gallery.ui.offline;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import com.lounah.gallery.data.repository.PhotosRepository;
import com.lounah.gallery.util.AbsentLiveData;
import com.lounah.gallery.util.FileUtil;

import java.io.File;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class OfflineFragmentViewModel extends ViewModel {

    private final MutableLiveData<Boolean> refreshState = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final PhotosRepository repository;

    private LiveData<List<String>> savedPhotosPaths;

    @Inject
    OfflineFragmentViewModel(@NonNull final PhotosRepository repository) {
        this.repository = repository;
        savedPhotosPaths = Transformations.switchMap(refreshState, shouldRefresh -> {
           if (shouldRefresh) return this.repository.getSavedPhotosFilePaths(FileUtil.savedPhotosDirectory());
           else return AbsentLiveData.create();
        });
    }

    LiveData<List<String>> getSavedPhotosPaths(@NonNull final File dir) {
        if (savedPhotosPaths == null) {
            savedPhotosPaths = new MutableLiveData<>();
        }
        return savedPhotosPaths;
    }

    void refresh() {
        refreshState.setValue(true);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!disposables.isDisposed())
            disposables.dispose();
    }
}
