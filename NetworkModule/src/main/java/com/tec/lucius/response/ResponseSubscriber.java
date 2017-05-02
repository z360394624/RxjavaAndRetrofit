package com.tec.lucius.response;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;

/**
 * Created by z3603 on 2017/5/2.
 */

public class ResponseSubscriber<ResponseBean> implements FlowableSubscriber<ResponseBean> {


    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(ResponseBean o) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
