package com.kennie.library.http.func;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kennie.library.http.gson.ZGsonBuilder;
import com.kennie.library.http.model.ApiResult;
import com.kennie.library.http.utils.Utils;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;


/**
 * 定义了ApiResult结果转换Func
 */
@SuppressWarnings("unchecked")
public class ApiResultFunc<T> implements Function<ResponseBody, ApiResult<T>> {
    protected Type type;
    protected Gson gson;

    public ApiResultFunc(Type type) {
        this.type = type;
        gson = ZGsonBuilder.gsonBuilder2(type).create();

    }

    @Override
    public ApiResult<T> apply(@NonNull ResponseBody responseBody) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(-1);

        try {
            String json = responseBody.string();
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            apiResult.setMsg(e.getMessage());
        } finally {
            responseBody.close();
        }

        return apiResult;
    }
}
