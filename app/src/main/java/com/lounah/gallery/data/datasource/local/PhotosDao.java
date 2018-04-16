package com.lounah.gallery.data.datasource.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.lounah.gallery.data.entity.Photo;

import java.util.List;

@Dao
public interface PhotosDao extends BaseDao<Photo> {

    /*
        Получает фотографии, которые были взяты из Яндекс.Диска
     */
    @Query("SELECT * FROM photos WHERE inTrash=0")
    LiveData<List<Photo>> getFeed();

    /*
        Получает фотографии, добавленные в корзину
     */
    @Query("SELECT * FROM photos WHERE inTrash=1")
    LiveData<List<Photo>> getDeletedPhotos();

    /*
        Получить размер сохраненных в локальной БД фотографий
     */
    @Query("SELECT COUNT(*) FROM photos")
    int getPersistedPhotosSize();

    /*
        Очистить БД
     */
    @Query("DELETE FROM photos")
    void eraseAll();

}
