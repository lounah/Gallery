package com.lounah.gallery.data.datasource.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T object);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<T> objects);

    @Update
    void update(T object);

    @Update
    void updateAll(List<T> objects);

    @Delete
    void delete(T object);

    @Delete
    void deleteAll(List<T> objects);

}
