package com.lounah.gallery.data.datasource.remote;


import com.lounah.gallery.BuildConfig;
import com.lounah.gallery.data.entity.Photo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GalleryService {

    @Headers("Authorization: OAuth " + BuildConfig.AUTH_TOKEN)
    @GET("resources/last-uploaded")
    Single<List<Photo>> getFeed();
}
