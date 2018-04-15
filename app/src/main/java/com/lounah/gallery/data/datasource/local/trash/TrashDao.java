package com.lounah.gallery.data.datasource.local.trash;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.lounah.gallery.data.datasource.local.BaseDao;
import com.lounah.gallery.data.entity.Trash;

import java.util.List;
@Dao
public interface TrashDao extends BaseDao<Trash> {

    @Query("SELECT * FROM trash")
    LiveData<List<Trash>> getDeletedPhotos();

    @Query("DELETE FROM trash")
    void eraseAll();

}
