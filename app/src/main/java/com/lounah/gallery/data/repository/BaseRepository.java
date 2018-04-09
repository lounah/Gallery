package com.lounah.gallery.data.repository;


import android.arch.lifecycle.LiveData;

import com.lounah.gallery.data.entity.Resource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface BaseRepository<T> {

    LiveData<Resource<List<T>>> get();

    Completable add(final T data);

}
