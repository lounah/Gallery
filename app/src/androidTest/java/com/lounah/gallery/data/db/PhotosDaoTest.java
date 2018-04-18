package com.lounah.gallery.data.db;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.lounah.gallery.data.util.LiveDataTestUtil;
import com.lounah.gallery.data.datasource.local.AppDatabase;
import com.lounah.gallery.data.datasource.local.PhotosDao;
import com.lounah.gallery.data.entity.Photo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PhotosDaoTest {

    private final Photo testPhoto = new Photo(0, "testPath",
            "previewDownloadLink", "name",
            "fileDownloadLink", "date", false);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;
    private PhotosDao photosDao;

    @Before
    public void init() {

        testPhoto.setId("Hgjskdk2352kjfsdnrh23k23rdJKAkAkd32");

        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .allowMainThreadQueries()
                .build();

        photosDao = mDatabase.photosDao();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void returnsEmptyList_if_dbIsEmpty() throws InterruptedException {
        final List<Photo> photos = LiveDataTestUtil.getValue(photosDao.getFeed());
        assertTrue(photos.isEmpty());
    }

    @Test
    public void insertsPhoto() throws InterruptedException {

        photosDao.insert(testPhoto);

        assertEquals(1, photosDao.getPersistedPhotosSize());

        assertEquals(0, LiveDataTestUtil.getValue(photosDao.getDeletedPhotos()).size());
    }

    @Test
    public void deletesPhoto() throws InterruptedException {

        photosDao.insert(testPhoto);

        testPhoto.setInTrash(true);

        photosDao.update(testPhoto);

        assertEquals(1, LiveDataTestUtil.getValue(photosDao.getDeletedPhotos()).size());
    }

    @Test
    public void deletesPhotoPermanently() {

        photosDao.insert(testPhoto);

        assertEquals(1, photosDao.getPersistedPhotosSize());

        photosDao.delete(testPhoto);

        assertEquals(0, photosDao.getPersistedPhotosSize());
    }

    @Test
    public void movesFromTrashToGallery() throws InterruptedException {

        photosDao.insert(testPhoto);

        testPhoto.setInTrash(false);

        photosDao.update(testPhoto);

        assertEquals(0, LiveDataTestUtil.getValue(photosDao.getDeletedPhotos()).size());

        assertEquals(1, photosDao.getPersistedPhotosSize());
    }
}
