package com.tec.lucius;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.tec.lucius.request.RetrofitRequest;
import com.tec.lucius.response.Context;
import com.tec.lucius.response.ResponseBean;
import com.tec.lucius.response.ResponseSubscriber;

import org.reactivestreams.Subscription;

import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import retrofit2.Response;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String BASE_URL = "http://192.168.0.105:8080/";

        String url = "account/login";



        Network.init(BASE_URL);

        FlowableSubscriber<Response<ResponseBean<Context>>> s = new FlowableSubscriber<Response<ResponseBean<Context>>>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Response<ResponseBean<Context>> response) {
                Log.d("ooooo","o.getData().respMessage = " + response.body().getHttpStatus());
                Log.d("ooooo","o.getData().respMessage = " + response.body().getHttpMessage());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

//        FlowableSubscriber<String> s = new FlowableSubscriber<String>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//                s.request(Long.MAX_VALUE);
//            }
//
//            @Override
//            public void onNext(Response<String> stringResponse) {
//                Log.d("ooooo","o.getData().respMessage = " + simpleResponseResponseBean.getHttpMessage());
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };


        new SimpleRequest().method(RetrofitRequest.POST).execute(url, new HashMap<String, Object>(), s);
    }
}
