package com.kennie.library.rxhttp.request.exception;

/**
 */
public class NullRequestSettingException extends RuntimeException {
    public NullRequestSettingException() {
        super("RequestSetting未设置");
    }
}
