package com.kennie.library.rxhttp.request.exception;


import androidx.annotation.NonNull;

import com.google.gson.JsonParseException;
import com.kennie.library.rxhttp.core.RxHttp;
import com.kennie.library.rxhttp.request.utils.NetUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLException;

import com.kennie.library.rxhttp.request.setting.RequestSetting;

import retrofit2.HttpException;

/**
 * 集中处理请求中异常，可通过继承自定义，在
 * {@link RequestSetting#getExceptionHandle()}中返回
 */
public class ExceptionHandle {

    private Throwable e;
    private int code;
    private String msg;

    public final void handle(@NonNull Throwable e) {
        this.e = e;
        this.code = onGetCode(e);
        this.msg = onGetMsg(code);
    }

    /**
     * 重写该方法去返回异常对应的错误码
     *
     * @param e Throwable
     * @return 错误码
     */
    protected int onGetCode(@NonNull Throwable e) {
        if (!NetUtils.isConnected()) {
            return ErrorCode.NET;
        } else {
            if (e instanceof HttpException) {
                return ErrorCode.HTTP;
            } else if (e instanceof SocketTimeoutException) {
                return ErrorCode.TIMEOUT;
            } else if (e instanceof UnknownHostException || e instanceof ConnectException) {
                return ErrorCode.HOST;
            } else if (e instanceof JsonParseException || e instanceof ParseException || e instanceof JSONException) {
                return ErrorCode.JSON;
            } else if (e instanceof SSLException) {
                return ErrorCode.SSL;
            } else {
                return ErrorCode.UNKNOWN;
            }
        }
    }

    /**
     * 重写该方法去返回错误码对应的错误信息
     *
     * @param code 错误码
     * @return 错误信息
     */
    @NonNull
    protected String onGetMsg(int code) {
        String msg;
        switch (code) {
            default:
                msg = "未知错误，请稍后重试";
                break;
            case ErrorCode.NET:
                msg = "网络连接失败，请检查网络设置";
                break;
            case ErrorCode.HTTP:
                msg = "请求错误，请稍后重试";
                break;
            case ErrorCode.TIMEOUT:
                msg = "网络状况不稳定，请稍后重试";
                break;
            case ErrorCode.HOST:
                msg = "服务器连接失败，请检查网络设置";
                break;
            case ErrorCode.JSON:
                msg = "JSON解析异常";
                break;
            case ErrorCode.SSL:
                msg = "证书验证失败";
                break;
        }
        return msg;
    }

    public final int getCode() {
        return code;
    }

    public final String getMsg() {
        return msg;
    }

    public final Throwable getException() {
        return e;
    }

    public interface ErrorCode {
        // 网络错误
        int NET = 1000;
        // HTTP请求错误，请稍后重试
        int HTTP = NET + 1;
        // 服务器响应的超时
        int TIMEOUT = HTTP + 1;
        // 无法解析该域名 | 服务器请求超时
        int HOST = TIMEOUT + 1;
        // JSON数据解析异常
        int JSON = HOST + 1;
        // 证书验证失败
        int SSL = JSON + 1;
        // 未知错误
        int UNKNOWN = -1;
    }
}
