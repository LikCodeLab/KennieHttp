
package com.kennie.example.http.customapi.test5;


import com.kennie.library.http.model.ApiResult;

/**
 * <p>描述：自定义ApiResult，使用情景举列5</p>
 * 作者： zhouyou<br>
 * 日期： 2017/7/6 17:52 <br>
 * 版本： v1.0<br>
 * 没有code、msg；只有一个集合List
 */
public class TestApiResult5<T> extends ApiResult<T> {
   
    @Override
    public boolean isOk() {
        return true;//因为此数据结构没有code,就是不去判断code的值了,认为是成功的
    }
}
