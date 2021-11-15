package com.kennie.library.rxhttp.request.base;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.kennie.library.rxhttp.request.utils.JsonFormatUtils;

import java.io.Serializable;


/**
 * 描述：网络请求的实体类基类
 *
 */
public class BaseBean implements Serializable {

    @NonNull
    public String toJson() {
        return new Gson().toJson(this);
    }

    @NonNull
    public String toFormatJson() {
        return JsonFormatUtils.format(toJson());
    }
}