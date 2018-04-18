package com.lounah.gallery.util;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;

import javax.inject.Inject;

/*
    Не знаю, насколько этот класс нам нужен;
    Позволяет получить ссылку на директорию, в котором приложение хранит сохраненные фото

    Приложение падать, если устройство пользователя не предусматривает наличие SD

 */
public class FileUtil {

    private static final String APP_DIRECTORY_NAME = "lounah";

    private static final File savedPhotosExternalDir = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), APP_DIRECTORY_NAME);

    private static final File savedPhotosInternalDir = new File(Environment.getDataDirectory(), APP_DIRECTORY_NAME);

    private FileUtil() {}

    // TODO: fix crush, when external is unavailable
    public static File savedPhotosDirectory() {

        File savedPhotosDir = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (!savedPhotosExternalDir.exists()) savedPhotosExternalDir.mkdir();
            savedPhotosDir = savedPhotosExternalDir;
        } else if (!savedPhotosInternalDir.exists()) {
                savedPhotosInternalDir.mkdir();
                savedPhotosDir = savedPhotosInternalDir;
            }
        return savedPhotosDir;
    }
}
