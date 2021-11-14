package com.kennie.library.http.func;

import com.kennie.library.http.exception.ApiException;
import com.kennie.library.http.exception.ServerException;
import com.kennie.library.http.model.ApiResult;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * ApiResult<T>转换T
 */
public class HandleFuc<T> implements Function<ApiResult<T>, T> {
    @Override
    public T apply(@NonNull ApiResult<T> tApiResult) throws Exception {
        if (ApiException.isOk(tApiResult)) {
            return tApiResult.getData();// == null ? Optional.ofNullable(tApiResult.getData()).orElse(null) : tApiResult.getData();
        } else {
            throw new ServerException(tApiResult.getCode(), tApiResult.getMsg());
        }
    }
}
