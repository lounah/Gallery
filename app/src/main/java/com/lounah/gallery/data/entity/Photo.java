package com.lounah.gallery.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "photos")
public class Photo implements Parcelable {

    @NonNull
    @PrimaryKey
    @SerializedName("sha256")
    private String id = "";

    @SerializedName("path")
    private String path;

    @SerializedName("size")
    private long size;

    @SerializedName("preview")
    private String previewDownloadLink;

    @SerializedName("name")
    private String name;

    @SerializedName("file")
    private String fileDownloadLink;

    @SerializedName("created")
    private String date;

    @Ignore
    public Photo() {}

    public Photo(long size, String path, String previewDownloadLink, String name, String fileDownloadLink, String date) {
        this.size = size;
        this.previewDownloadLink = previewDownloadLink;
        this.name = name;
        this.fileDownloadLink = fileDownloadLink;
        this.date = date;
        this.path = path;
    }

    protected Photo(Parcel in) {
        id = in.readString();
        path = in.readString();
        size = in.readLong();
        previewDownloadLink = in.readString();
        name = in.readString();
        fileDownloadLink = in.readString();
        date = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public String getPreviewDownloadLink() {
        return previewDownloadLink;
    }

    public String getName() {
        return name;
    }

    public String getFileDownloadLink() {
        return fileDownloadLink;
    }

    public String getDate() {
        return date;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(path);
        dest.writeLong(size);
        dest.writeString(previewDownloadLink);
        dest.writeString(name);
        dest.writeString(fileDownloadLink);
        dest.writeString(date);
    }
}
