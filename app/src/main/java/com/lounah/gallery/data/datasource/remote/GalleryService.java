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

    /*
        Получить список фотографий, хранящихся на Диске

        @param limit -- лимит на количество получаемых фотографий (по умолчанию 20)
        @param media_type -- тип объекта, получаемого из Диска
     */
    @Headers("Authorization: OAuth " + AUTH_TOKEN)
    @GET("resources/last-uploaded")
    Single<List<Photo>> getFeed(
            @Query("limit") final int limit,
            @Query("media_type") final String mediaType
    );

    /*
        Удалить фотографию из Диска в Корзину

        @param path -- путь к фотографии на Диске
        @param permanently(true/false) -- удалить навсегда/переместить в корзину
     */
    @Headers("Authorization: OAuth " + AUTH_TOKEN)
    @DELETE("resources")
    Completable deletePhoto(
      @Query("path") @NonNull final String path,
      @Query("permanently") final boolean permanently
    );

    /*
        Очистить корзину
     */
    @Headers("Authorization: OAuth " + AUTH_TOKEN)
    @DELETE("trash/resources")
    Completable clearTrash();

    /*
        Переместить фотографию из Корзины обратно на Диск

        @param path -- путь к фотографии
     */
    @Headers("Authorization: OAuth " + AUTH_TOKEN)
    @PUT("trash/resources/restore")
    Completable movePhotoToGallery(
            @Query("path") @NonNull final String path
    );

    /*
        Получить ссылку на загрузку фотографии
        (не реализовано)
     */
    @Headers("Authorization: OAuth " + AUTH_TOKEN)
    @GET("resources/upload")
    Single<String> getUploadUrl(
        @Query("path") @NonNull final String resourceName,
        @Query("fields") @NonNull final String... fields
    );

    /*
        Загрузить фото
        (не реализовано)
     */
    @PUT
    Completable uploadPhoto(
            @Url @NonNull final String uploadUrl,
            @Part MultipartBody.Part image
    );


}
