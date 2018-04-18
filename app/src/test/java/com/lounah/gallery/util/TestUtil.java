package com.lounah.gallery.util;


import com.lounah.gallery.data.entity.Photo;

public class TestUtil {

    private TestUtil() {
    }

    public static Photo createPhoto() {

        final Photo photo = new Photo(0, "testPath",
                "previewDownloadLink", "name",
                "fileDownloadLink", "date", false);

        photo.setId("Hgjskdk2352kjfsdnrh23k23rdJKAkAkd32");

        return photo;
    }
}
