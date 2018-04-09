package com.lounah.gallery.data.datasource.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.lounah.gallery.data.datasource.local.feed.FeedDao;
import com.lounah.gallery.data.entity.Photo;

@Database(entities = {Photo.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FeedDao feedDao();

}

