package com.lounah.gallery.util;

import android.os.Environment;

import java.io.File;

/*
    Не знаю, насколько этот класс нам нужен;
    Позволяет получить ссылку на директорию, в котором приложение хранит сохраненные фото
 */
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
