package com.tec.lucius.request;

import android.support.annotation.IntDef;

import com.tec.lucius.Network;
import com.tec.lucius.network.ApiService;
import com.tec.lucius.response.Context;
import com.tec.lucius.response.ResponseBean;
import com.tec.lucius.response.ResponseSubscriber;
import com.tec.lucius.retrofit.RetrofitComponents;

import org.reactivestreams.Publisher;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Objects;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by z3603 on 2017/5/1.
 */

public class RetrofitRequest {

    public static final int POST = 8096;
    public static final int GET = 8097;
    public static final int CONNECT_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;


    private Retrofit mRetrofit;
    private FlowableTransformer mTransformer;
    @Method int mMethod = POST;
    @IntDef({POST, GET})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Method {}

    /**
     * 组件初始化顺序不可乱
     */
    public RetrofitRequest() {
        RetrofitComponents.ComponentsFactory().initDomain(getDomain());
        RetrofitComponents.ComponentsFactory().initConnectTimeout(getConnectTimeout());
        RetrofitComponents.ComponentsFactory().initWriteTimeout(getWriteTimeout());
        RetrofitComponents.ComponentsFactory().initReadTimeout(getReadTimeout());
        RetrofitComponents.ComponentsFactory().buildOkHttpClient();
        this.mRetrofit = RetrofitComponents.ComponentsFactory().buildRetrofit();
        this.mTransformer = schedulerTransformer();
    }

    public RetrofitRequest method(@Method int method) {
        this.mMethod = method;
        return this;
    }

    public FlowableTransformer schedulerTransformer() {
        return new FlowableTransformer() {
            @Override
            public Publisher apply(Flowable upstream) {
                return upstream.subscribeOn(getSubscribeScheduler())
                        .unsubscribeOn(getUnsubscribeScheduler())
                        .observeOn(getObserveScheduler());
            }
        };
    }

    private Retrofit getRetrofit() {
        return mRetrofit;
    }

    private FlowableTransformer getTransformer() {
        return mTransformer;
    }

    protected String getDomain() {
        return Network.DOMAIN;
    }

    protected int getConnectTimeout() {
        return CONNECT_TIMEOUT;
    }

    protected int getReadTimeout() {
        return READ_TIMEOUT;
    }

    protected int getWriteTimeout() {
        return WRITE_TIMEOUT;
    }

    protected Scheduler getSubscribeScheduler() {
        return Schedulers.io();
    }

    protected Scheduler getUnsubscribeScheduler() {
        return Schedulers.io();
    }

    protected Scheduler getObserveScheduler() {
        return Schedulers.io();
    }

    public void execute(String path, HashMap<String, Object> params, FlowableSubscriber subscriber) {
        ApiService service = getRetrofit().create(ApiService.class);
        service.post(path, params).compose(getTransformer()).subscribe(subscriber);
    }
}
