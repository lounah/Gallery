package com.lounah.gallery.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity
public class Trash implements Parcelable {

    @NonNull
    @PrimaryKey
    @SerializedName("sha256")
    private String id = "";
    private String downloadLink;
    private long size;
    private String name;
    private String date;

    @Ignore
    public Trash() {}

    public Trash(@NonNull String id, String downloadLink, long size, String name, String date) {
        this.id = id;
        this.downloadLink = downloadLink;
        this.size = size;
        this.name = name;
        this.date = date;
    }

    protected Trash(Parcel in) {
        id = in.readString();
        downloadLink = in.readString();
        size = in.readLong();
        name = in.readString();
        date = in.readString();
    }

    public static final Creator<Trash> CREATOR = new Creator<Trash>() {
        @Override
        public Trash createFromParcel(Parcel in) {
            return new Trash(in);
        }

        @Override
        public Trash[] newArray(int size) {
            return new Trash[size];
        }
    };

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull final String id) {
        this.id = id;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(downloadLink);
        dest.writeLong(size);
        dest.writeString(name);
        dest.writeString(date);
    }
}
