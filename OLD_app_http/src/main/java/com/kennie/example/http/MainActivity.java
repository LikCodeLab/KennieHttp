package com.kennie.example.http;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kennie.example.http.api.LoginService;
import com.kennie.example.http.config.AppConstant;
import com.kennie.example.http.config.ComParamContact;
import com.kennie.example.http.customapi.test5.TestApiResult5;
import com.kennie.example.http.model.ApiInfo;
import com.kennie.example.http.model.AuthModel;
import com.kennie.example.http.model.SectionItem;
import com.kennie.example.http.model.SkinTestResult;
import com.kennie.example.http.utils.FileUtils;
import com.kennie.example.http.utils.MD5;
import com.kennie.library.http.KennieHttp;
import com.kennie.library.http.callback.CallBack;
import com.kennie.library.http.callback.CallClazzProxy;
import com.kennie.library.http.callback.ProgressDialogCallBack;
import com.kennie.library.http.callback.SimpleCallBack;
import com.kennie.library.http.exception.ApiException;
import com.kennie.library.http.model.ApiResult;
import com.kennie.library.http.request.CustomRequest;
import com.kennie.library.http.subsciber.BaseSubscriber;
import com.kennie.library.http.subsciber.IProgressDialog;
import com.kennie.library.http.subsciber.ProgressSubscriber;
import com.kennie.library.http.utils.HttpLog;
import com.kennie.library.http.utils.RxUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.create(new ObservableOnSubscribe<String>() {
            @SuppressLint("CheckResult")
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                FileUtils.getFileFromAsset(MainActivity.this, "1.jpg");
            }
        }).compose(RxUtil.<String>io_main()).subscribe(new Consumer<String>() {
            @SuppressLint("CheckResult")
            @Override
            public void accept(@NonNull String s) throws Exception {

            }
        });
    }


    public void onLogin(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onGet(View view) {
        KennieHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .readTimeOut(30 * 1000)//局部定义读超时 ,可以不用定义
                .writeTimeOut(30 * 1000)
                .connectTimeout(30 * 1000)
                //.headers("","")//设置头参数
                //.params("name","张三")//设置参数
                //.addInterceptor()
                //.addConverterFactory()
                //.addCookie()
                .timeStamp(true)
                .execute(new SimpleCallBack<SkinTestResult>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        if (response != null) showToast(response.toString());
                    }
                });
               /* .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        if (response != null) showToast(response.toString());
                    }
                });*/
    }

    /**
     * post请求
     */
    public void onPost(View view) {
        KennieHttp.post("v1/app/chairdressing/news/favorite")
                .params("newsId", "552")
                .accessToken(true)
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showToast(response);
                    }
                });
    }

    /**
     * post提交Object
     */
    public void onPostObject(View view) {
        ApiInfo apiInfo = new ApiInfo();
        ApiInfo.ApiInfoBean apiInfoBean = apiInfo.new ApiInfoBean();
        apiInfoBean.setApiKey("12345");
        apiInfoBean.setApiName("zhou-you");
        apiInfo.setApiInfo(apiInfoBean);
        KennieHttp.post("client/shipper/getCarType")
                .baseUrl("http://WuXiaolong.me/")
                //如果是body的方式提交object，必须要加GsonConverterFactory.create()
                //他的本质就是把object转成json给到服务器，所以必须要加Gson Converter
                //切记！切记！切记！  本例可能地址不对只做演示用
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(apiInfo)//这种方式会自己把对象转成json提交给服务器
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage() + "  " + e.getCode());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showToast(response);
                    }
                });
    }

    /**
     * post提交json
     */
    public void onPostJson(View view) {
        KennieHttp.post("api/")
                .baseUrl("http://xxxx.xx.xx/dlydbg/")
                .upJson("{\"\":\"\",\"\":\"\",\"\":\"\",\"swry_dm\":\"127053096\",\"version\":\"1.0.0\"}")
                //这里不想解析，简单只是为了做演示 直接返回String
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        showToast(s);
                    }
                });
    }

    /**
     * put请求
     */
    public void onPut(View view) {
        //http://api.youdui.org/api/v1/cart/1500996?count=4
        KennieHttp.put("http://api.youdui.org/api/v1/cart/1500996")
                .removeParam("appId")
                .params("count", "4")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showToast(response);
                    }
                });
    }

    /**
     * delete请求
     */
    public void onDelete(View view) {
        //测试请用自己的URL，这里为了安全去掉了地址
        //这里采用的是delete请求提交json的方式，可以选择其他需要的方式
        KennieHttp.delete("https://www.xxx.com/v1/user/Frined")
                .upJson("{\"uid\":\"10008\",\"token\":\"5b305fbeaa331\"}\n")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showToast(response);
                    }
                });
    }

    /**
     * 基础回调
     */
    public void onCallBack(View view) {
        //支持CallBack<SkinTestResult>、CallBack<String>回调
        Disposable mDisposable = KennieHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(new CallBack<SkinTestResult>() {
                    @Override
                    public void onStart() {
                        showToast("开始请求");
                    }

                    @Override
                    public void onCompleted() {
                        showToast("请求完成");
                    }

                    @Override
                    public void onError(ApiException e) {
                        showToast("请求失败：" + e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        showToast("请求成功：" + response.toString());
                    }
                });
    }

    /**
     * 简单回调
     *
     * @param view
     */
    public void onSimpleCallBack(View view) {
        //支持SimpleCallBack<SkinTestResult>、SimpleCallBack<String>回调
        KennieHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(new SimpleCallBack<SkinTestResult>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        showToast(response.toString());
                    }
                });
    }

    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("请稍候...");
            return dialog;
        }
    };

    /**
     * 带有加载进度框的回调，支持是否可以取消对话框，取消对话框时可以自动取消网络请求，不需要再手动取消。
     */
    public void onProgressDialogCallBack(View view) {
        KennieHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(new ProgressDialogCallBack<SkinTestResult>(mProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);//super.onError(e)必须写不能删掉或者忘记了
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        showToast(response.toString());
                    }
                });
    }

    /**
     * 请求网络接口最终获取Subscription对象，通过该对象手动取消网络请求
     * 在需要取消网络请求的地方调用,一般在onDestroy()中
     */
    public void onSubscription(View view) {
        Disposable disposable = KennieHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(new SimpleCallBack<SkinTestResult>() {

                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        showToast(response.toString());
                    }
                });

        //KennieHttp.cancelSubscription(disposable);

    }

    /**
     * 返回Observable对象，Observable是Rxjava，有了Observable可以与其它业务逻辑很好的结合
     * 本例不讲怎么结合简单订阅下输出结果，结合需要看具体场景
     */
    public void onObservable(View view) {
        Observable<SkinTestResult> observable = KennieHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(SkinTestResult.class);

        observable.subscribe(new BaseSubscriber<SkinTestResult>() {
            @Override
            public void onError(ApiException e) {
                showToast(e.getMessage());
            }

            @Override
            public void onNext(SkinTestResult skinTestResult) {
                showToast(skinTestResult.toString());
            }
        });
    }

    /**
     * 带有进度框的订阅者，对话框消失，可以自动取消掉网络请求
     */
    public void onProgressSubscriber(View view) {
        Observable<SkinTestResult> observable = KennieHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(SkinTestResult.class);

        observable.subscribe(new ProgressSubscriber<SkinTestResult>(this, mProgressDialog) {
            @Override
            public void onError(ApiException e) {
                super.onError(e);
                showToast(e.getMessage());
            }

            @Override
            public void onNext(SkinTestResult skinTestResult) {
                showToast(skinTestResult.toString());
            }
        });
    }

    /**
     * 上传文件
     */
    public void onUploadFile(View view) {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }

    /**
     * 上传文件
     */
    public void onDownloadFile(View view) {
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivity(intent);
    }

    /**
     * 同步请求
     */
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public void onSync(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                KennieHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                        .readTimeOut(30 * 1000)//局部定义读超时
                        .writeTimeOut(30 * 1000)
                        .connectTimeout(30 * 1000)
                        .timeStamp(true)
                        .syncRequest(true)//设置同步请求
                        .execute(new SimpleCallBack<SkinTestResult>() {
                            @Override
                            public void onError(final ApiException e) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showToast(e.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void onSuccess(final SkinTestResult response) {
                                mHandler.post(new Runnable() {//异步 Toast
                                    @Override
                                    public void run() {
                                        if (response != null) showToast(response.toString());
                                    }
                                });
                            }
                        });
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HttpLog.i("====同步请求==========");
                KennieHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                        .readTimeOut(30 * 1000)//局部定义读超时
                        .writeTimeOut(30 * 1000)
                        .connectTimeout(30 * 1000)
                        .syncRequest(true)//设置同步请求
                        .timeStamp(true)
                        .execute(new SimpleCallBack<SkinTestResult>() {
                            @Override
                            public void onError(final ApiException e) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showToast(e.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void onSuccess(final SkinTestResult response) {
                                mHandler.post(new Runnable() {//异步 Toast
                                    @Override
                                    public void run() {
                                        if (response != null) showToast(response.toString());
                                    }
                                });
                            }
                        });
            }
        }).start();
    }

    /**
     * 网络缓存
     */
    public void onCache(View view) {
        Intent intent = new Intent(this, CacheActivity.class);
        startActivity(intent);
    }

    /**
     * 使用KennieHttp调用自定义api  注意：如果有签名的注意路径有"/"的情况如下
     * https://www.xxx.com/v1/account/login （正确）
     * https://www.xxx.com//v1/account/login (错误 可能会导致签名失败)
     */
    public void onCustomCall(View view) {
        final String name = "18688994275";
        final String pass = "123456";
        final CustomRequest request = KennieHttp.custom().addConverterFactory(GsonConverterFactory.create(new Gson()))
                .sign(true)
                .timeStamp(true)
                .params(ComParamContact.Login.ACCOUNT, name)
                .params(ComParamContact.Login.PASSWORD, MD5.encrypt4login(pass, AppConstant.APP_SECRET))
                .build();

        LoginService mLoginService = request.create(LoginService.class);
        Observable<ApiResult<AuthModel>> observable = request.call(mLoginService.login("v1/account/login", request.getParams().urlParamsMap));
        Disposable disposable = observable.subscribe(new Consumer<ApiResult<AuthModel>>() {
            @Override
            public void accept(@NonNull ApiResult<AuthModel> result) throws Exception {
                showToast(result.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                showToast(throwable.getMessage());
            }
        });
        //KennieHttp.cancelSubscription(disposable);//取消订阅
    }

    public void onCustomApiCall(View view) {
        final String name = "18688994275";
        final String pass = "123456";
        final CustomRequest request = KennieHttp.custom()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .sign(true)
                .timeStamp(true)
                .params(ComParamContact.Login.ACCOUNT, name)
                .params(ComParamContact.Login.PASSWORD, MD5.encrypt4login(pass, AppConstant.APP_SECRET))
                .build();

        LoginService mLoginService = request.create(LoginService.class);
        Observable<AuthModel> observable = request.apiCall(mLoginService.login("v1/account/login", request.getParams().urlParamsMap));
        Disposable disposable = observable.subscribe(new Consumer<AuthModel>() {
            @Override
            public void accept(@NonNull AuthModel authModel) throws Exception {
                showToast(authModel.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                showToast(throwable.getMessage());
            }
        });
        //KennieHttp.cancelSubscription(disposable);//取消订阅
    }

    public void onCustomApiResult(View view) {
        Intent intent = new Intent(this, CustomApiActivity.class);
        startActivity(intent);
    }

    public void onListResult(View view) {
        //方式一：
       /* KennieHttp.get("http://news-at.zhihu.com/api/3/sections")
                .execute(new CallBackProxy<TestApiResult5<List<SectionItem>>, List<SectionItem>>(new SimpleCallBack<List<SectionItem>>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(List<SectionItem> sectionItems) {
                        showToast(sectionItems.toString());
                    }
                }) {
                });*/
        //方式二：
        /*Observable<List<SectionItem>> observable = KennieHttp.get("http://news-at.zhihu.com/api/3/sections")
                .execute(new CallClazzProxy<TestApiResult5<List<SectionItem>>, List<SectionItem>>(new TypeToken<List<SectionItem>>() {
                }.getType()) {
                });
        observable.subscribe(new Consumer<List<SectionItem>>() {
            @Override
            public void accept(@NonNull List<SectionItem> sectionItems) throws Exception {
                showToast(sectionItems.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                showToast(throwable.getMessage());
            }
        });*/
        //方式三：
        Observable<List<SectionItem>> observable = KennieHttp.get("http://news-at.zhihu.com/api/3/sections")
                .execute(new CallClazzProxy<TestApiResult5<List<SectionItem>>, List<SectionItem>>(new TypeToken<List<SectionItem>>() {
                }.getType()) {
                });
        observable.subscribe(new ProgressSubscriber<List<SectionItem>>(MainActivity.this, mProgressDialog) {
            @Override
            public void onError(ApiException e) {
                super.onError(e);
                showToast(e.getMessage());
            }

            @Override
            public void onNext(List<SectionItem> sectionItems) {
                showToast(sectionItems.toString());
            }
        });
    }

    public void onScene(View view) {
        Intent intent = new Intent(MainActivity.this, SceneActivity.class);
        startActivity(intent);
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
