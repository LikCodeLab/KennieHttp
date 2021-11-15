package com.kennie.library.rxhttp.download.exception;

/**
 */
public class SaveFileWriteException extends RuntimeException {
    public SaveFileWriteException() {
        super("下载保存的文件写入失败");
    }
}
