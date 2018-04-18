package com.lounah.gallery.di;


import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lounah.gallery.data.datasource.local.AppDatabase;
import com.lounah.gallery.data.datasource.local.PhotosDao;
import com.lounah.gallery.data.datasource.remote.GalleryService;
import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.data.repository.PhotosRepository;
import com.lounah.gallery.util.GalleryJsonDeserializer;


import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {ViewModelModule.class})
class AppModule {

    private static final String API_URL = "https://cloud-api.yandex.net:443/v1/disk/";
    private static final String APP_DB_NAME = "gallery.db";

    @Singleton
    @Provides
    GalleryService provideGalleryRemoteService() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .registerTypeAdapter(new TypeToken<List<Photo>>(){}.getType(),
                                new GalleryJsonDeserializer()).create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GalleryService.class);
    }

    @Singleton
    @Provides
    AppDatabase provideDb(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, APP_DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    PhotosDao providePhotosDao(AppDatabase db) {
        return db.photosDao();
    }

    @Singleton
    @Provides
    PhotosRepository providePhotosRepository(@NonNull final GalleryService api,
                                             @NonNull final PhotosDao photosDao) {
        return new PhotosRepository(api, photosDao);
    }

}
