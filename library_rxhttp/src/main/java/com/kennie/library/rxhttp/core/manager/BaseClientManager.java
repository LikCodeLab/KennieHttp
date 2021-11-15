package com.kennie.library.rxhttp.core.manager;


import androidx.annotation.NonNull;

import retrofit2.Retrofit;

/**
 * 用于管理Retrofit实例
 * 子类继承后自行判断是否采用单例模式
 */
public abstract class BaseClientManager {
    @NonNull
    protected abstract Retrofit create();
}
