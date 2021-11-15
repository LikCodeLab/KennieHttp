package com.kennie.library.rxhttp.download.exception;

/**
 */
public class SaveFileDirMakeException extends RuntimeException {
    public SaveFileDirMakeException() {
        super("下载保存的文件父文件夹创建失败");
    }
}
