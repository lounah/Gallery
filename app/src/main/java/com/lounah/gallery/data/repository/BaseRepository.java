package com.lounah.gallery.data.repository;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface BaseRepository<T> {

    Single<List<T>> get();

    Completable add(final T data);

}
