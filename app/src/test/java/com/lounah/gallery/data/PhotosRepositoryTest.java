package com.lounah.gallery.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.lounah.gallery.data.datasource.local.AppDatabase;
import com.lounah.gallery.data.datasource.local.PhotosDao;
import com.lounah.gallery.data.datasource.remote.GalleryService;
import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.data.entity.Resource;
import com.lounah.gallery.data.repository.PhotosRepository;
import com.lounah.gallery.util.AbsentLiveData;
import com.lounah.gallery.util.TestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
    Здесь все ужасно, и я даже не знаю, стоит ли это коммитить
 */

@RunWith(JUnit4.class)
public class PhotosRepositoryTest {

    @Mock
    GalleryService service;

    @Mock
    private PhotosDao dao;

    @Mock
    private AppDatabase db;

    private final Photo testPhoto = TestUtil.createPhoto();

    private PhotosRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(db.photosDao()).thenReturn(dao);
        repository = new PhotosRepository(service, dao);
    }

    @Test
    public void getNumRows_isEmptyOnFirstLaunch() {
        when(dao.getPersistedPhotosSize()).thenReturn(0);
        assertThat(repository.getNumRowsFromFeed(), is(0));
    }

    @Test
    public void getFeed_loadsFromNetworkWhenLocalDBIsEmpty() {
        MutableLiveData<Resource<List<Photo>>> feed = new MutableLiveData<>();
        when(repository.getFeed(true)).thenReturn(feed);


    }

    @Test
    public void deletesPhoto() {

    }
}
