package com.lounah.gallery.data.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.lounah.gallery.data.entity.Status.ERROR;
import static com.lounah.gallery.data.entity.Status.LOADING;
import static com.lounah.gallery.data.entity.Status.SUCCESS;

public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    private Resource(@NonNull Status status, @Nullable T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data);
    }

    public static <T> Resource<T> error(@Nullable T data) {
        return new Resource<>(ERROR, data);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data);
    }

}