package com.lounah.gallery.data.datasource.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.lounah.gallery.data.datasource.local.feed.FeedDao;
import com.lounah.gallery.data.datasource.local.trash.TrashDao;
import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.data.entity.Trash;

@Database(entities = {Photo.class, Trash.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FeedDao feedDao();

    public abstract TrashDao trashDao();
}

