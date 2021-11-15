package com.kennie.library.rxhttp.core.utils;

import android.os.Environment;

import androidx.annotation.NonNull;

import com.kennie.library.rxhttp.core.RxHttp;

import java.io.File;


/**
 */
public class SDCardUtils {

    @NonNull
    public static String getCacheDir() {
        File cacheFile = null;
        if (isSDCardAlive()) {
            cacheFile = RxHttp.getAppContext().getExternalCacheDir();
        }
        if (cacheFile == null) {
            cacheFile = RxHttp.getAppContext().getCacheDir();
        }
        return cacheFile.getAbsolutePath();
    }

    @NonNull
    public static String getDownloadCacheDir() {
        File dir = null;
        if (isSDCardAlive()) {
            dir = RxHttp.getAppContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        }
        if (dir == null) {
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        }
        return dir.getAbsolutePath();
    }

    private static boolean isSDCardAlive() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}