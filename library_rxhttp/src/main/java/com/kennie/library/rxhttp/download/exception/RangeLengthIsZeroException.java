package com.kennie.library.rxhttp.download.exception;

/**
 */
public class RangeLengthIsZeroException extends RuntimeException {
    public RangeLengthIsZeroException() {
        super("断点处请求长度为0");
    }
}
