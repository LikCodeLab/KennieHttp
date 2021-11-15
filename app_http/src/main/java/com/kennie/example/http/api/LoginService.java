

package com.kennie.example.http.api;




import com.kennie.example.http.model.AuthModel;
import com.kennie.library.http.model.ApiResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {
    @POST("{path}")
    @FormUrlEncoded
    Observable<ApiResult<AuthModel>> login(@Path("path") String path, @FieldMap Map<String, String> map);
}
