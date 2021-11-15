package com.kennie.library.rxhttp.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 */
public interface DownloadApi{
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String range, @Url String url);
}
