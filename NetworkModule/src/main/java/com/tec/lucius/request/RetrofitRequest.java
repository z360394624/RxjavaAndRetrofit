package com.tec.lucius.request;

import com.tec.lucius.Network;
import com.tec.lucius.retrofit.RetrofitComponents;

import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.Objects;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by z3603 on 2017/5/1.
 */

public class RetrofitRequest {

    public static int CONNECT_TIMEOUT = 15;
    public static int READ_TIMEOUT = 15;
    public static int WRITE_TIMEOUT = 15;

    /**
     * 组件初始化顺序不可乱
     */
    public RetrofitRequest() {
        RetrofitComponents.ComponentsFactory().initDomain(getDomain());
        RetrofitComponents.ComponentsFactory().initConnectTimeout(getConnectTimeout());
        RetrofitComponents.ComponentsFactory().initWriteTimeout(getWriteTimeout());
        RetrofitComponents.ComponentsFactory().initReadTimeout(getReadTimeout());
        RetrofitComponents.ComponentsFactory().buildOkHttpClient();
        RetrofitComponents.ComponentsFactory().buildRetrofit();
        schedulerTransformer();
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

    public void execute(String path, HashMap<String, Object> params) {

    }
}
