package com.kennie.library.rxhttp.request.interceptor;

import androidx.annotation.NonNull;

import com.kennie.library.rxhttp.request.Api;
import com.kennie.library.rxhttp.request.utils.NetUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 描述：缓存过滤器
 * 用于为Response配置缓存策略
 *
 */
public class CacheControlNetworkInterceptor extends BaseCacheControlInterceptor {

    public static void addTo(@NonNull OkHttpClient.Builder builder) {
        builder.addNetworkInterceptor(new CacheControlNetworkInterceptor());
    }

    private CacheControlNetworkInterceptor() {
    }

    @NonNull
    @Override
    protected Request getCacheRequest(@io.reactivex.annotations.NonNull Request request, int age) {
        return request.newBuilder()
                .removeHeader(Api.Header.CACHE_ALIVE_SECOND)
                .build();
    }

    @NonNull
    @Override
    protected Response getCacheResponse(@io.reactivex.annotations.NonNull Response response, int age) {
        if (NetUtils.isConnected()) {
            if (age <= 0) {
                return response.newBuilder()
                        .removeHeader("Cache-Control")
                        .build();
            } else {
                return response.newBuilder()
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + age)
                        .build();
            }
        } else {
            return response.newBuilder()
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + Integer.MAX_VALUE)
                    .build();
        }
    }
}