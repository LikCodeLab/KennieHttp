package com.kennie.library.http.callback;

import java.lang.reflect.Type;

/**
 * 获取类型接口
 */
public interface IType<T> {
    Type getType();
}
