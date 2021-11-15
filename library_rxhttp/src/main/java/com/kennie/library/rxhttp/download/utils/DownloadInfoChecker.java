package com.kennie.library.rxhttp.download.utils;

import android.text.TextUtils;

import com.kennie.library.rxhttp.core.RxHttp;
import com.kennie.library.rxhttp.core.utils.SDCardUtils;
import com.kennie.library.rxhttp.download.DownloadInfo;
import com.kennie.library.rxhttp.download.exception.RangeLengthIsZeroException;
import com.kennie.library.rxhttp.download.exception.SaveFileBrokenPointException;

import java.io.File;



/**
 * 描述：
 *
 */
public class DownloadInfoChecker {

    public static void checkDownloadLength(DownloadInfo info) throws SaveFileBrokenPointException {
        if (info.downloadLength == 0){
            File file = createFile(info.saveDirPath, info.saveFileName);
            if (file != null && file.exists()) {
                if (info.mode == DownloadInfo.Mode.APPEND) {
                    info.downloadLength = file.length();
                } else if (info.mode == DownloadInfo.Mode.REPLACE) {
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                } else {
                    assert info.saveFileName != null;
                    info.saveFileName = renameFileName(info.saveFileName);
                }
            }
        } else {
            File file = createFile(info.saveDirPath, info.saveFileName);
            if (file != null && file.exists()) {
                if (info.downloadLength != file.length()) {
                    throw new SaveFileBrokenPointException();
                }
            } else {
                info.downloadLength = 0;
            }
        }
    }

    public static void checkContentLength(DownloadInfo info) throws RangeLengthIsZeroException {
        //noinspection ConditionCoveredByFurtherCondition
        if (info.downloadLength > 0 && info.contentLength > 0 && info.contentLength <= info.downloadLength) {
            throw new RangeLengthIsZeroException();
        }
    }
    
    private static String renameFileName(String fileName){
        String nameLeft;
        String nameDivide;
        String nameRight;
        int index = fileName.lastIndexOf(".");
        if (index >= 0) {
            nameLeft = fileName.substring(0, index);
            nameDivide = ".";
            nameRight = fileName.substring(index + 1);
        } else {
            nameLeft = fileName;
            nameDivide = "";
            nameRight = "";
        }
        int k1 = nameLeft.lastIndexOf("(");
        int k2 = nameLeft.lastIndexOf(")");
        int i = 1;
        if (k2 + 1 == nameLeft.length() && k1 >= 0 && k2 >= 0 && k2 > k1) {
            String num = nameLeft.substring(k1 + 1, k2);
            nameLeft = nameLeft.substring(0, k1);
            try {
                i = Integer.parseInt(num);
                i += 1;
            } catch (NumberFormatException ignore){
            }
        }
        return nameLeft + "(" + i + ")" + nameDivide + nameRight;
    }

    private static File createFile(String dirPath, String fileName){
        if (TextUtils.isEmpty(dirPath) || TextUtils.isEmpty(fileName)) {
            return null;
        }
        return new File(dirPath, fileName);
    }

    public static void checkDirPath(DownloadInfo info){
        if (TextUtils.isEmpty(info.saveDirPath)) {
            info.saveDirPath = RxHttp.getDownloadSetting().getSaveDirPath();
        }
        if (TextUtils.isEmpty(info.saveDirPath)) {
            info.saveDirPath = SDCardUtils.getDownloadCacheDir();
        }
    }

    public static void checkFileName(DownloadInfo info){
        if (TextUtils.isEmpty(info.saveFileName)) {
            info.saveFileName = System.currentTimeMillis() + ".rxdownload";
        }
    }

}
