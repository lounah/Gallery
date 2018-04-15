package com.lounah.gallery.data.datasource.local.feed;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.lounah.gallery.data.datasource.local.BaseDao;
import com.lounah.gallery.data.entity.Photo;
import java.util.List;

@Dao
public interface FeedDao extends BaseDao<Photo> {

    @Query("SELECT * FROM photos")
    LiveData<List<Photo>> getPhotos();

    @Query("DELETE from photos WHERE path=:path")
    void delete(final String path);

    @Query("SELECT COUNT(*) FROM photos")
    int getNumRows();

    @Query("DELETE FROM photos")
    void eraseAll();
}
