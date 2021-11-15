package com.kennie.library.rxhttp.core;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.kennie.library.rxhttp.core.exception.RxHttpUninitializedException;
import com.kennie.library.rxhttp.download.DownloadInfo;
import com.kennie.library.rxhttp.download.RxDownload;
import com.kennie.library.rxhttp.download.setting.DefaultDownloadSetting;
import com.kennie.library.rxhttp.download.setting.DownloadSetting;
import com.kennie.library.rxhttp.request.RxRequest;
import com.kennie.library.rxhttp.request.base.BaseResponse;
import com.kennie.library.rxhttp.request.exception.NullRequestSettingException;
import com.kennie.library.rxhttp.request.setting.RequestSetting;

import io.reactivex.Observable;


/**
 * 描述：
 */
@SuppressLint("StaticFieldLeak")
public class RxHttp {

    private static RxHttp INSTANCE = null;

    private final Context mAppContext;
    private RequestSetting mRequestSetting = null;
    private DownloadSetting mDownloadSetting = null;

    private RxHttp(@NonNull Context context) {
        mAppContext = context;
    }

    public static void init(@NonNull Context context) {
        INSTANCE = new RxHttp(context.getApplicationContext());
    }

    @NonNull
    public static RxHttp getInstance() {
        if (INSTANCE == null) {
            throw new RxHttpUninitializedException();
        }
        return INSTANCE;
    }

    public static void initRequest(@NonNull RequestSetting setting) {
        getInstance().mRequestSetting = setting;
    }

    public static void initDownload(@NonNull DownloadSetting setting) {
        getInstance().mDownloadSetting = setting;
    }

    @NonNull
    public static Context getAppContext() {
        return getInstance().mAppContext;
    }

    @NonNull
    public static RequestSetting getRequestSetting() {
        RequestSetting setting = getInstance().mRequestSetting;
        if (setting == null) {
            throw new NullRequestSettingException();
        }
        return setting;
    }

    @NonNull
    public static DownloadSetting getDownloadSetting() {
        DownloadSetting setting = getInstance().mDownloadSetting;
        if (setting == null) {
            setting = new DefaultDownloadSetting();
        }
        return setting;
    }

    public static <T, R extends BaseResponse<T>> RxRequest<T, R> request(@NonNull Observable<R> observable) {
        return RxRequest.create(observable);
    }

    public static RxDownload download(@NonNull DownloadInfo info) {
        return RxDownload.create(info);
    }
}
