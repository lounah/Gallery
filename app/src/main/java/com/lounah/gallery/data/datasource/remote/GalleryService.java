package com.lounah.gallery.data.datasource.remote;


import android.support.annotation.NonNull;

import com.lounah.gallery.BuildConfig;
import com.lounah.gallery.data.entity.Photo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GalleryService {

    String AUTH_TOKEN = BuildConfig.AUTH_TOKEN;

    @Headers("Authorization: OAuth " + AUTH_TOKEN)
    @GET("resources/last-uploaded")
    Single<List<Photo>> getFeed(
            @Query("limit") final int limit,
            @Query("media_type") final String mediaType
    );

    @Headers("Authorization: OAuth " + AUTH_TOKEN)
    @DELETE("resources")
    Completable deletePhoto(
      @Query("path") @NonNull final String path,
      @Query("permanently") final boolean permanently
    );

    @Headers("Authorization: OAuth " + AUTH_TOKEN)
    @DELETE("trash/resources")
    Completable clearTrash();

    @Headers("Authorization: OAuth " + AUTH_TOKEN)
    @PUT("trash/resources/restore")
    Completable movePhotoToGallery(
            @Query("path") @NonNull final String path
    );

    @Headers("Authorization: OAuth " + AUTH_TOKEN)
    @GET("resources/upload")
    Single<String> getUploadUrl(
        @Query("path") @NonNull final String resourceName,
        @Query("fields") @NonNull final String... fields
    );


    @PUT
    Completable uploadPhoto(
            @Url @NonNull final String uploadUrl,
            @Part MultipartBody.Part image
    );


}
