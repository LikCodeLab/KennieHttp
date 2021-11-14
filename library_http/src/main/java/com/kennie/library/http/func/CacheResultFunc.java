package com.kennie.library.http.func;

import com.kennie.library.http.cache.model.CacheResult;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 缓存结果转换
 */
public class CacheResultFunc<T> implements Function<CacheResult<T>, T> {
    @Override
    public T apply(@NonNull CacheResult<T> tCacheResult) throws Exception {
        return tCacheResult.data;
    }
}
