package com.lounah.gallery.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "photos")
public class Photo {

    @NonNull
    @PrimaryKey
    @SerializedName("sha256")
    private String id = "";

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

    public Photo(long size, String previewDownloadLink, String name, String fileDownloadLink, String date) {
        this.size = size;
        this.previewDownloadLink = previewDownloadLink;
        this.name = name;
        this.fileDownloadLink = fileDownloadLink;
        this.date = date;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setPreviewDownloadLink(String previewDownloadLink) {
        this.previewDownloadLink = previewDownloadLink;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFileDownloadLink(String fileDownloadLink) {
        this.fileDownloadLink = fileDownloadLink;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
