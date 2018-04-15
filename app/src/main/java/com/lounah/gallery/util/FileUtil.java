package com.lounah.gallery.util;

import android.os.Environment;

import java.io.File;

public class FileUtil {

    private static final String APP_DIRECTORY_NAME = "lounah";
    private static final File savedPhotosDir = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), APP_DIRECTORY_NAME);

    private FileUtil() {}

    public static File savedPhotosDirectory() {
        if (!savedPhotosDir.exists()) savedPhotosDir.mkdir();
            return savedPhotosDir;
    }
}
