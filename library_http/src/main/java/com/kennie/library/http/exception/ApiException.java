package com.kennie.library.http.exception;

/**
 * @项目名 KennieHttp
 * @类名称 ApiException
 * @类描述 异常信息统一处理类
 * @创建人 Administrator
 * @修改人
 * @创建时间 2021/11/28 9:45
 */
public class ApiException extends RuntimeException {


    private int errorCode; // 异常码
    private String errorMsg; // 异常信息

    public ApiException() {
    }

    public ApiException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMsg = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
